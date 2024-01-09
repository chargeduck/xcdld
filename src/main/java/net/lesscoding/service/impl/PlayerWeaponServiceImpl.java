package net.lesscoding.service.impl;
import java.time.LocalDateTime;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.DiscardLog;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.DiscardLogMapper;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.mapper.WeaponMapper;
import net.lesscoding.model.dto.query.QueryWeaponDto;
import net.lesscoding.model.vo.PlayerWeaponVo;
import net.lesscoding.service.DiscardLogService;
import net.lesscoding.service.PlayerWeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eleven
 * @date 2023/11/21 15:46
 * @apiNote
 */
@Service
public class PlayerWeaponServiceImpl extends ServiceImpl<PlayerWeaponMapper, PlayerWeapon> implements PlayerWeaponService {

    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;
    @Autowired
    private DiscardLogMapper discardLogMapper;
    @Autowired
    private WeaponMapper weaponMapper;
    @Autowired
    private AccountPlayerMapper  accountPlayerMapper;

    @Override
    public List<PlayerWeaponVo> getPlayerAllWeapon(QueryWeaponDto dto) {
        dto.setAccountId(StpUtil.getLoginIdAsInt());
        return playerWeaponMapper.getPlayerAllWeapon(dto);
    }

    @Override
    public Integer discardWeapon(QueryWeaponDto dto) {
        checkLoginUser(dto);
        // 品级 0白 1绿 2蓝 3紫 4粉 5传说 6史诗
        if (dto.getGrade() >= 4) {
            DiscardLog discardLog = new DiscardLog();
            discardLog.setObjId(dto.getId());
            discardLog.setType(0);
            discardLog.setPlayerId(dto.getPlayerId());
            discardLog.setDiscardType(0);
            discardLog.setReason("玩家主动丢弃");
            discardLogMapper.insert(discardLog);
            return playerWeaponMapper.discardWeapon(dto);
        }
        return playerWeaponMapper.deleteById(dto.getId());
    }

    private static void checkLoginUser(QueryWeaponDto dto) {
        String loginId = StpUtil.getLoginIdAsString();
        if (StrUtil.equalsAnyIgnoreCase(loginId, String.valueOf(dto.getAccountId()))) {
            throw new RuntimeException("不允许丢弃他人武器");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Integer sellWeapon(QueryWeaponDto dto) {
        checkLoginUser(dto);
        Weapon weapon = weaponMapper.selectById(dto.getWeaponId());
        accountPlayerMapper.addGold(weapon.getSellingPrice(), dto.getAccountId());
        // 品级 0白 1绿 2蓝 3紫 4粉 5传说 6史诗
        if (dto.getGrade() >= 4) {
            DiscardLog discardLog = new DiscardLog();
            discardLog.setObjId(dto.getId());
            discardLog.setType(0);
            discardLog.setPlayerId(dto.getPlayerId());
            discardLog.setDiscardType(1);
            discardLog.setReason("玩家主动售卖");
            discardLogMapper.insert(discardLog);
            return playerWeaponMapper.discardWeapon(dto);
        }
        return playerWeaponMapper.deleteById(dto.getId());
    }


}
