package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.model.dto.query.QueryWeaponDto;
import net.lesscoding.model.vo.PlayerWeaponVo;
import net.lesscoding.service.PlayerWeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<PlayerWeaponVo> getPlayerAllWeapon(QueryWeaponDto dto) {
        dto.setAccountId(StpUtil.getLoginIdAsString());
        return playerWeaponMapper.getPlayerAllWeapon(dto);
    }

    @Override
    public Object getPlayerWeaponDetail(QueryWeaponDto dto) {

        return null;
    }
}
