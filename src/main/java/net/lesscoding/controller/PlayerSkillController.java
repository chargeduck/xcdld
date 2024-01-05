package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.query.QuerySkillDto;
import net.lesscoding.service.PlayerSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2024/1/4 16:42
 * @apiNote
 */
@RestController
@RequestMapping("/playerSkill")
public class PlayerSkillController {

    @Autowired
    private PlayerSkillService playerSkillService;

    @PostMapping("/getAll")
    public Result getAllSkill(@RequestBody QuerySkillDto dto) {
        return ResultFactory.success(playerSkillService.getAllSkill(dto));
    }

    @PostMapping("/upgrade")
    public Result upgradeSkill(@RequestBody QuerySkillDto dto) {
        return ResultFactory.success(playerSkillService.upgradeSkill(dto));
    }
}
