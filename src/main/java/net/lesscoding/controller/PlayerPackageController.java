package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.PropsOperationDto;
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

    /**
     * 丢弃道具
     * @param dto
     * @return
     */
    @PostMapping("/discard")
    public Result discardProps(@RequestBody PropsOperationDto dto) {
        return ResultFactory.success(playerPackageService.discardProps(dto));
    }

    /**
     * 售卖道具
     * @param dto
     * @return
     */
    @PostMapping("/sell")
    public Result sellProps(@RequestBody PropsOperationDto dto) {
        return ResultFactory.success(playerPackageService.sellProps(dto));
    }

    /**
     * 打开道具
     * @param dto
     * @return
     */
    @PostMapping("/open")
    public Result openProps(@RequestBody PropsOperationDto dto) {
        return ResultFactory.success(playerPackageService.openProps(dto));
    }
}
