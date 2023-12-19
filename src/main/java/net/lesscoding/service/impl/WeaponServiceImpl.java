package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.WeaponMapper;
import net.lesscoding.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/8 12:47
 * @apiNote
 */
@Service
public class WeaponServiceImpl extends ServiceImpl<WeaponMapper,Weapon>implements WeaponService {

    @Autowired
    private WeaponMapper weaponMapper;

    @Override
    public Integer editWeapon(Weapon weapon) {
        return weapon.getId() == null ?
                weaponMapper.insert(weapon) :
                weaponMapper.updateById(weapon);
    }
}
