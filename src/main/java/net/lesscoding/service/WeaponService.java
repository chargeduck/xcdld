package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.Weapon;

/**
 * @author eleven
 * @date 2023/10/8 12:47
 * @apiNote
 */
public interface WeaponService extends IService<Weapon> {
    /**
     * 编辑武器数据
     * @param weapon    武器数据
     * @return Integer  保存记录数
     */
    Integer editWeapon(Weapon weapon);
}
