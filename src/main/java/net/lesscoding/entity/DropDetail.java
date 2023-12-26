package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/31 9:04
 * @apiNote
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_props_drop_detail")
public class DropDetail extends BaseEntity {

    /**
     * 道具id
     */
    private Integer propsId;

    /**
     * 物品id
     */
    private Integer objId;

    /**
     * 类型 0消耗品 1技能 2武器
     */
    private Integer type;

    /**
     * 掉落概率
     */
    private Double dropProbability;
}
