package net.lesscoding.model.vo;

import lombok.Data;
import org.apache.logging.log4j.message.ReusableMessage;

import java.text.NumberFormat;

/**
 * @author eleven
 * @date 2023/10/26 11:02
 * @apiNote
 */
@Data
public class PlayerInfoVo {
    private Integer id;

    private String account;

    private Integer level;
    /**
     * 金币
     */
    private Long gold;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 血量
     */
    private Integer hp;

    /**
     * 攻击力
     */
    private Integer attack;

    /**
     * 防御力
     */
    private Integer defence;

    /**
     * 命中率
     */
    private Double hitRate;

    /**
     * 闪避率
     */
    private Double flee;

    /**
     * 连击率
     */
    private Double comboRate;
    /**
     * 暴击率
     */
    private Double criticalChance;

    private Integer exp;

    private Integer nextLvExp;

    private Integer energy;

    private Integer maxEnergy;

    private String token;

    private String goldNum;

    public Long getGold() {
        return gold == null ? 0 : gold;
    }

    public String getGoldNum() {
        // 创建一个 NumberFormat 对象，指定格式化模式为千分符
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        // 使用 format() 方法将 long 类型的数字转换成千分符展示的字符串
        return numberFormat.format(getGold());
    }

}
