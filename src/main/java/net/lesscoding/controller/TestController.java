package net.lesscoding.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author eleven
 * @date 2025/3/18 17:56
 * @apiNote
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/{id}")
    @SaIgnore
    public Integer idTest(@PathVariable Integer id) {
        return RandomUtil.randomInt(1, 100) * id;
    }
}
