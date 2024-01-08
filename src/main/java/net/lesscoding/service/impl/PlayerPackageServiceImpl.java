package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.PlayerPackage;
import net.lesscoding.entity.SysDict;
import net.lesscoding.mapper.PlayerPackageMapper;
import net.lesscoding.mapper.SysDictMapper;
import net.lesscoding.model.dto.query.QueryPackageEntryDto;
import net.lesscoding.model.vo.PackagePropsVo;
import net.lesscoding.service.PlayerPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


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
    private SysDictMapper sysDictMapper;

    @Override
    public List<PlayerPackage> findByType(Integer playerId,Integer type) {
        LambdaQueryWrapper<PlayerPackage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayerPackage::getPlayerId,playerId);
        queryWrapper.eq(PlayerPackage::getType,type);
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
