package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/8 12:50
 * @apiNote
 */
@Data
@TableName("tb_skill")
public class Skill extends BaseEntity {
    /**
     * 技能类型 0被动增加属性类 1被动触发 2主动技能
     */
    private Integer skillType;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能描述
     */
    private String intro;

    /**
     * 技能品级
     */
    private Integer grade;

    /**
     * 是否固定伤害 true 固定伤害 false 百分比提升
     */
    private Boolean fixed;
}
