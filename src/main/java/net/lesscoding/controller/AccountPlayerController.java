package net.lesscoding.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.service.AccountPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author eleven
 * @date 2023/10/10 16:59
 * @apiNote
 */
@RestController
@RequestMapping("/player")
public class AccountPlayerController {

    @Autowired
    private AccountPlayerService playerService;

    @PostMapping("/getAll")
    public Result getAllPlayer(@RequestBody PlayerDto dto) {
        List<String> loginUsers = StpUtil.searchSessionId("", 0, -1, true);
        return ResultFactory.success(String.valueOf(CollUtil.size(loginUsers)), playerService.getAllPlayer(dto));
    }

    @PostMapping("/detail")
    public Result getPlayerDetail(@RequestBody AccountPlayer dto) {
        return ResultFactory.success(playerService.getPlayerDetail(dto));
    }

    @PutMapping("/recovery")
    public Result recoveryEnergy() throws IOException {
        return ResultFactory.success(playerService.selfRecovery());
    }
}
