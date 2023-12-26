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

    /**
     * 丢弃武器
     * @param dto       武器id
     * @return Result
     */
    @PostMapping("/discard")
    public Result discardWeapon(@RequestBody QueryWeaponDto dto) {
        return ResultFactory.success(playerWeaponService.discardWeapon(dto));
    }

    /**
     * 售卖武器
     * @param dto   武器id
     * @return Result
     */
    @PostMapping("/sell")
    public Result sellWeapon(@RequestBody QueryWeaponDto dto) {
        return ResultFactory.success(playerWeaponService.sellWeapon(dto));
    }

    /**
     * 强化武器
     * @param dto   武器id
     * @return Result
     */
    @PostMapping("/enhanced")
    public Result enhancedWeapon(@RequestBody QueryWeaponDto dto) {
        return ResultFactory.failed("功能暂未实现");
    }

}
