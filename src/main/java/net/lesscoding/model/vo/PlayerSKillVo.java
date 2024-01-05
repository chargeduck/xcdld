package net.lesscoding.model.vo;

import lombok.Data;
import net.lesscoding.entity.ThingsEntry;
import net.lesscoding.utils.EntryUtil;

import java.util.List;

/**
 * @author eleven
 * @date 2024/1/4 17:16
 * @apiNote
 */
@Data
public class PlayerSKillVo {
    /**
     *
     */
    private Integer id;
    /**
     * 账号
     */
    private Integer accountId;
    /**
     * 技能名称
     */
    private String skillName;
    /**
     * 技能描述
     */
    private String skillIntro;
    /**
     * 是否主动技能
     */
    private Boolean activeSkill;
    /**
     * 是否固定伤害
     */
    private Boolean fixedSkill;
    /**
     * 技能等级
     */
    private Integer skillLevel;
    /**
     * 最小伤害
     */
    private Integer minDamage;
    /**
     * 最大伤害
     */
    private Integer maxDamage;
    /**
     * 物品列表
     */
    private List<ThingsEntry> thingsEntries;

    /**
     * 属性描述
     */
    private List<String> thingEntriesDesc;

    public List<String> getThingEntriesDesc() {
        return EntryUtil.getEntriesStr(thingsEntries);
    }
}
