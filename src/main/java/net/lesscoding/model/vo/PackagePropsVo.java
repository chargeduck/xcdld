package net.lesscoding.model.vo;

import lombok.Data;

/**
 * @author eleven
 * @date 2024/1/5 16:34
 * @apiNote
 */
@Data
public class PackagePropsVo {
    /**
     * 背包id
     */
    private Integer id;
    /**
     * 道具id
     */
    private Integer objId;

    /**
     * 道具名称
     */
    private String propsName;

    /**
     * 道具描述
     */
    private String propsIntro;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 类型 0 锦囊 1礼包 2材料 3技能卡 4宝石 5消耗品
     */
    private Integer type;


    /**
     * 售价
     */
    private Integer sellingPrice;

    /**
     * 可以使用
     */
    private Boolean canUse;

    /**
     * 允许打开
     */
    private Boolean allowOpen;

    /**
     * 爆率
     */
    private Double dropProbability;

}
