package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.query.QueryPackageEntryDto;
import net.lesscoding.service.PlayerPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2023/11/21 15:41
 * @apiNote
 */
@RestController
@RequestMapping("/package")
public class PlayerPackageController {

    @Autowired
    private PlayerPackageService playerPackageService;

    /**
     * 获取所有的道具
     * @param dto       查询参数
     * @return Result   返回结果
     */
    @PostMapping("/getAllProps")
    public Result getAllProps(@RequestBody QueryPackageEntryDto dto) {
        return ResultFactory.success(playerPackageService.getAllProps(dto));
    }
}
