package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/8 13:24
 * @apiNote
 */
@Data
@TableName("tb_player_level_exp")
public class PlayerLevelExp extends BaseEntity {

    /**
     * 等级
     */
    private Integer level;

    /**
     * 所需经验
     */
    private Integer needExp;

    /**
     * 基础攻击力
     */
    private Integer attack;
    /**
     * 基础防御力
     */
    private Integer defender;
    /**
     * 基础血量
     */
    private Integer hp;
    /**
     * 基础闪避率
     */
    private Double flee;
    /**
     * 基础练级
     */
    private Double combo;
    /**
     * 基础命中
     */
    private Double hit;

    /**
     * 暴击率
     */
    private Double criticalChance;
    /**
     * 特殊效果类型
     */
    @TableField(exist = false)
    private Integer specialEffectType;

}
