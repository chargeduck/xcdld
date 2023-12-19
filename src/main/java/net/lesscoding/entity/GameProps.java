package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/31 9:07
 * @apiNote
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_game_props")
public class GameProps extends BaseEntity<GameProps> {

    /**
     * 名称
     */
    private String propsName;
    /**
     * 道具描述
     */
    private String propsIntro;

    /**
     * 掉落概率
     */
    private Double dropProbability;

    /**
     * 等级 0 白 1蓝 2紫 3粉 4传说 5史诗 6神话 7贴膜
     */
    private Integer grade;

    /**
     * 0 锦囊 1礼包 2材料 3技能卡 4宝石 5消耗品
     */
    private Integer type;
    /**
     * 是否允许打开
     */
    private Boolean allowOpen;

    private Boolean canUse;

    /**
     * 售价
     */
    private Integer sellingPrice;

}
