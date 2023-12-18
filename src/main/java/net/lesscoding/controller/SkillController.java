package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.save.SKillSaveDto;
import net.lesscoding.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2023/12/16 15:49
 * @apiNote
 */
@RestController
@RequestMapping("/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping("/edit")
    public Result editSKill(@RequestBody SKillSaveDto dto) {
        return ResultFactory.success(skillService.editSkill(dto));
    }
}
