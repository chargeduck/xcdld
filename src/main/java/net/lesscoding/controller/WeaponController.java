package net.lesscoding.controller;

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

}
