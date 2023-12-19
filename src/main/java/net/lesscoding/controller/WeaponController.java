package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.Weapon;
import net.lesscoding.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author eleven
 * @date 2023/10/8 12:46
 * @apiNote 武器控制器
 */
@RestController
@RequestMapping("/weapon")
public class WeaponController {
    @Autowired
    private WeaponService weaponService;

    @PostMapping("/edit")
    private Result editWeapon(@RequestBody Weapon weapon) {
        return ResultFactory.success(weaponService.editWeapon(weapon));
    }
}
