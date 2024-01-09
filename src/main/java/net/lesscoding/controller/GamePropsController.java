package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.GameProps;
import net.lesscoding.service.GamePropsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2023/12/19 9:16
 * @apiNote
 */
@RestController
@RequestMapping("/gameProps")
public class GamePropsController {
    @Autowired
    private GamePropsService propsService;

    @PostMapping("/edit")
    public Result editGameProps(@RequestBody GameProps props) {
        return ResultFactory.success(propsService.editGameProps(props));
    }

    @PostMapping("/updateIntro")
    public Result updatePropsIntro(@RequestBody GameProps props) {
        return ResultFactory.success(propsService.updatePropsIntro(props));
    }
}
