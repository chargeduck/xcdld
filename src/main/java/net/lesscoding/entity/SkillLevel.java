package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/8 13:17
 * @apiNote
 */
@Data
@TableName("tb_skill_level")
public class SkillLevel extends BaseEntity {

    /**
     * 技能id
     */
    private Integer skillId;

    /**
     * 技能等级
     */
    private Integer level;
    /**
     * 需要的玩家等级
     */
    private Integer needPlayerLevel;

    /**
     *  增加伤害
     */
    private Double addAttack;

    /**
     * 增加防御
     */
    private Double addDefender;
    /**
     * 增加HP
     */
    private Double addHp;
    /**
     * 增加命中率
     */
    private Double addHitRate;
    /**
     * 增加闪避率
     */
    private Double addFlee;
    /**
     * 增加连击率
     */
    private Double addCombo;




}
