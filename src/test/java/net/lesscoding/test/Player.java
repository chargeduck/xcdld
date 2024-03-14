package net.lesscoding.test;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/20 8:39
 * @apiNote
 */
@Data
public class Player {
    private Integer lv;

    private Integer hp;

    public Player() {
        this.lv = RandomUtil.randomInt(1,31);
        this.hp = lv * 50;
    }
}
