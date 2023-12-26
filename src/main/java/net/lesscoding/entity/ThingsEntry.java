package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/12/15 15:27
 * @apiNote 物品词条
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_things_entry")
public class ThingsEntry extends BaseEntity {
    /**
     * 物品id
     */
    private Integer thingsId;
    /**
     * 词条id
     */
    private Integer entryId;
    /**
     * 固定/百分比
     */
    private Boolean fixed;
    /**
     * 数值
     */
    private Double num;
    /**
     * 类型
     * 0 武器 1技能 2称号 3光环 4宠物
     */
    private Integer type;

    /**
     *
     */
    @TableField(exist = false)
    private String entryName;

    @TableField(exist = false)
    private String entryIntro;
}
