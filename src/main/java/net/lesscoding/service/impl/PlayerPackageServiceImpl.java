package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.*;
import net.lesscoding.mapper.*;
import net.lesscoding.model.dto.PropsOperationDto;
import net.lesscoding.model.dto.query.QueryPackageEntryDto;
import net.lesscoding.model.vo.OperaPropsVo;
import net.lesscoding.model.vo.PackagePropsVo;
import net.lesscoding.service.PlayerPackageService;
import net.lesscoding.utils.OpenPackageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author eleven
 * @date 2023/10/31 9:47
 * @apiNote
 */
@Service
public class PlayerPackageServiceImpl extends ServiceImpl<PlayerPackageMapper, PlayerPackage> implements PlayerPackageService {

    @Autowired
    private PlayerPackageMapper playerPackageMapper;
    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;
    @Autowired
    private PlayerSKillMapper playerSKillMapper;
    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private DiscardLogMapper discardLogMapper;
    @Autowired
    private AccountPlayerMapper accountPlayerMapper;
    @Autowired
    private GamePropsMapper gamePropsMapper;
    @Autowired
    private WeaponMapper weaponMapper;
    @Autowired
    private SKillMapper sKillMapper;

    @Override
    public List<PlayerPackage> findByType(Integer playerId,Integer type) {
        LambdaQueryWrapper<PlayerPackage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayerPackage::getPlayerId,playerId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean addPlayerPackage(Integer playerId, List<PlayerPackage> playerPackages) {
        return false;
    }

    /**
     * 获取所有材料
     * @param   dto         查询参数
     * @return  List        返回参数
     */
    @Override
    public List<PackagePropsVo> getAllProps(QueryPackageEntryDto dto) {
        List<String> propsTypes = getPropsTypes(dto.getType());
        dto.setPropsType(propsTypes);
        dto.setAccountId(StpUtil.getLoginIdAsInt());
        return playerPackageMapper.selectPackageProps(dto);
    }

    @Override
    public OperaPropsVo discardProps(PropsOperationDto dto) {
        checkProps(dto);
        PackagePropsVo packagePropsVo = getPackagePropsVo(dto);
        // 品级 0白 1绿 2蓝 3紫 4粉 5传说 6史诗
        if (packagePropsVo.getGrade() >= 4) {
            DiscardLog discardLog = new DiscardLog();
            discardLog.setObjId(dto.getId());
            discardLog.setType(0);
            discardLog.setPlayerId(dto.getPlayerId());
            discardLog.setDiscardType(0);
            discardLog.setReason("玩家主动丢弃");
            discardLogMapper.insert(discardLog);
            return new OperaPropsVo(playerPackageMapper.discardProps(dto), "丢弃成功");
        }
        return new OperaPropsVo(playerPackageMapper.deleteById(dto.getId()), "丢弃成功");
    }

    private PackagePropsVo getPackagePropsVo(PropsOperationDto dto) {
        QueryPackageEntryDto queryDto = new QueryPackageEntryDto();
        queryDto.setPlayerId(dto.getPlayerId());
        queryDto.setId(dto.getId());
        List<PackagePropsVo> packagePropsVos = playerPackageMapper.selectPackageProps(queryDto);
        return packagePropsVos.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public OperaPropsVo sellProps(PropsOperationDto dto) {
        checkProps(dto);
        PackagePropsVo packagePropsVo = getPackagePropsVo(dto);
        accountPlayerMapper.addGold(packagePropsVo.getSellingPrice(), dto.getAccountId());
        // 品级 0白 1绿 2蓝 3紫 4粉 5传说 6史诗
        if (packagePropsVo.getGrade() >= 4) {
            DiscardLog discardLog = new DiscardLog();
            discardLog.setObjId(dto.getId());
            discardLog.setType(0);
            discardLog.setPlayerId(dto.getPlayerId());
            discardLog.setDiscardType(1);
            discardLog.setReason("玩家主动售卖");
            discardLogMapper.insert(discardLog);
            return new OperaPropsVo(playerPackageMapper.discardProps(dto), StrUtil.format("获得{}金币", packagePropsVo.getSellingPrice()));
        }
        return new OperaPropsVo(playerPackageMapper.deleteById(dto.getId()), StrUtil.format("获得{}金币", packagePropsVo.getSellingPrice()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public OperaPropsVo openProps(PropsOperationDto dto) {
        checkProps(dto);
        PackagePropsVo packagePropsVo = getPackagePropsVo(dto);
        List<DropDetail> propsDropList = packagePropsVo.getDropDetailList();
        if (CollUtil.isEmpty(propsDropList)) {
            throw new RuntimeException("当前物品未开放掉落物品");
        }
        List<DropDetail> dropDetailList = new ArrayList<>();
        for (long i = 0L; i < dto.getNumber(); i++) {
            dropDetailList.add(OpenPackageUtil.getDropDetail(propsDropList));
        }
        StringBuffer sb = new StringBuffer();
        dropPropsAndInsert(dto, dropDetailList, sb);
        dropSkillAndInsert(dto, dropDetailList, sb);
        dropWeaponAndInsert(dto, dropDetailList, sb);
        dropGold(dto, dropDetailList, sb);
        playerPackageMapper.substractProps(dto);
        return new OperaPropsVo(1, sb.toString());
    }

    private void dropPropsAndInsert(PropsOperationDto dto, List<DropDetail> dropDetailList, StringBuffer sb) {
        List<DropDetail> dropProps = OpenPackageUtil.getDropDetailList(dropDetailList, 0);
        if (CollUtil.isNotEmpty(dropProps)) {
            Map<Integer, String> propsMap = gamePropsMapper.selectList(
                        new QueryWrapper<GameProps>().in("id", OpenPackageUtil.getDropObjIds(dropProps))
                    )
                    .stream()
                    .collect(Collectors.toMap(GameProps::getId, GameProps::getPropsName));
            List<PlayerPackage> insertProps = new ArrayList<>();
            List<PlayerPackage> dataRows = playerPackageMapper.selectList(new QueryWrapper<PlayerPackage>()
                    .eq("del_flag", false)
                    .eq("player_id", dto.getPlayerId()));
            Map<Integer, PlayerPackage> dataMap = dataRows.stream()
                    .collect(Collectors.toMap(PlayerPackage::getObjId, Function.identity()));
            PlayerPackage insertEntity = null;
            for (DropDetail item : dropProps) {
                sb.append(StrUtil.format("获得道具【{}】* {}", propsMap.get(item.getObjId()), item.getNum()));
                PlayerPackage playerPackage = dataMap.get(item.getObjId());
                if (playerPackage == null) {
                    insertEntity = new PlayerPackage();
                    insertEntity.setObjId(item.getObjId());
                    insertEntity.setNum(item.getNum());
                    insertEntity.setType(2);
                    insertEntity.setPlayerId(dto.getPlayerId());
                    insertProps.add(insertEntity);
                } else {
                    playerPackage.setUpdateBy(StpUtil.getLoginIdAsInt());
                    playerPackage.setUpdateTime(LocalDateTime.now());
                    playerPackage.setNum(playerPackage.getNum() + item.getNum());
                    insertProps.add(playerPackage);
                }
            }
            playerPackageMapper.insertBatch(insertProps);
        }
    }
    private void dropWeaponAndInsert(PropsOperationDto dto, List<DropDetail> dropDetailList, StringBuffer sb) {
        List<DropDetail> dropWeapons = OpenPackageUtil.getDropDetailList(dropDetailList, 1);
        if (CollUtil.isNotEmpty(dropWeapons)) {
            Map<Integer, String> propsMap = weaponMapper.selectList(new QueryWrapper<Weapon>().in("id", OpenPackageUtil.getDropObjIds(dropWeapons)))
                    .stream()
                    .collect(Collectors.toMap(Weapon::getId, Weapon::getName));
            List<PlayerWeapon> insertProps = new ArrayList<>();
            PlayerWeapon insertEntity = null;
            for (DropDetail item : dropWeapons) {
                sb.append(StrUtil.format("获得武器【{}】* {}", propsMap.get(item.getObjId()), item.getNum()));
                insertEntity = new PlayerWeapon();
                insertEntity.setPlayerId(dto.getPlayerId());
                insertEntity.setWeaponId(item.getObjId());
                insertEntity.setAddLevel(0);
                insertProps.add(insertEntity);
            }
            playerWeaponMapper.insertBatch(insertProps);
        }
    }
    private void dropSkillAndInsert(PropsOperationDto dto, List<DropDetail> dropDetailList, StringBuffer sb) {
        List<DropDetail> dropSKills = OpenPackageUtil.getDropDetailList(dropDetailList, 2);
        if (CollUtil.isNotEmpty(dropSKills)) {
            Map<Integer, String> propsMap = sKillMapper.selectList(new QueryWrapper<Skill>().in("id", OpenPackageUtil.getDropObjIds(dropSKills)))
                    .stream()
                    .collect(Collectors.toMap(Skill::getId, Skill::getSkillName));
            List<PlayerSkill> insertProps = new ArrayList<>();
            PlayerSkill insertEntity = null;
            for (DropDetail item : dropSKills) {
                sb.append(StrUtil.format("获得技能【{}】* {}", propsMap.get(item.getObjId()), item.getNum()));
                insertEntity = new PlayerSkill();
                insertEntity.setPlayerId(dto.getPlayerId());
                insertEntity.setSkillId(item.getObjId());
                insertEntity.setSkillLevel(0);
                insertProps.add(insertEntity);
            }
            playerSKillMapper.insertBatch(insertProps);
        }
    }

    private void dropGold(PropsOperationDto dto, List<DropDetail> dropDetailList, StringBuffer sb) {
        List<DropDetail> dropGolds = OpenPackageUtil.getDropDetailList(dropDetailList, 3);
        if (CollUtil.isNotEmpty(dropGolds)) {
            Long addGoldNum = dropGolds.stream()
                    .mapToInt(DropDetail::getNum)
                    .count();
            dropGolds.forEach(item -> sb.append(StrUtil.format("获得金币 * {}", addGoldNum)));
            accountPlayerMapper.addGold(addGoldNum.intValue(), dto.getAccountId());
        }
    }
    /**
     * 校验各种参数
     *
     * @param dto 使用参数
     * @apiNote - id        不允许为空  <br/>
     * - number    不允许小于0  大于库存则默认全部打开<br/>
     * - playerId  不允许为空   不允许打开别人的物品<br/>
     */
    private void checkProps(PropsOperationDto dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("id不能为空");
        }

        if (dto.getNumber() < 0) {
            throw new RuntimeException("数量不能小于0");
        }
        if (dto.getPlayerId() == null) {
            throw new RuntimeException("玩家id不允许为空");
        }

        PlayerPackage dataRow = playerPackageMapper.selectById(dto.getId());
        if (dataRow == null || dataRow.getDelFlag() ) {
            throw  new RuntimeException("用户不存在该道具或已被删除");
        }
        if (dto.getNumber() > dataRow.getNum() || dataRow.getNum() == 0) {
            throw  new RuntimeException("用户不存在该道具");
        }
        if (!dataRow.getPlayerId().equals(dto.getPlayerId())) {
            throw  new RuntimeException("不允许打开其他玩家的道具");
        }
        dto.setNumber(Math.min(dto.getNumber(), dataRow.getNum()));
        dto.setAccountId(StpUtil.getLoginIdAsInt());
    }

    private List<String> getPropsTypes(String dictCode) {
        SysDict dict = sysDictMapper.selectByTypeAndCode("propsType", dictCode);
        if (dict == null) {
            String str = StrUtil.format("根据{}和{}查询字典项失败", "propsType", dictCode);
            throw new RuntimeException(str);
        }
        String dictValue = dict.getDictValue();
        return Arrays.asList(dictValue.split(","));
    }
}
