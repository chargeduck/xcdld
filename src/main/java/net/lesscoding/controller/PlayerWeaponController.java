package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.model.dto.query.QueryWeaponDto;
import net.lesscoding.service.PlayerWeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2023/11/21 15:45
 * @apiNote
 */
@RestController
@RequestMapping("/playerWeapon")
public class PlayerWeaponController {
    @Autowired
    private PlayerWeaponService playerWeaponService;

    @PostMapping("/getAll")
    public Result getPlayerAllWeapon(@RequestBody QueryWeaponDto dto) {
        return ResultFactory.success(playerWeaponService.getPlayerAllWeapon(dto));
    }

    @PostMapping("/detail")
    public Result getPlayerWeaponDetail(@RequestBody QueryWeaponDto dto) {
        return ResultFactory.success(playerWeaponService.getPlayerWeaponDetail(dto));
    }
}
