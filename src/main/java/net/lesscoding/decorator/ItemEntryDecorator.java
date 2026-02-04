package net.lesscoding.decorator;

import lombok.Data;

/**
 * @author eleven
 * @date 2025/2/25 16:09
 * @apiNote 物品条目装饰器
 */
@Data
public abstract class ItemEntryDecorator {
    /**
     * 存放 用户 / 武器 / 技能 的物品条目
     * <pre>
     * 用户：提升基础属性
     * 技能：提升技能效果（技能等级不同附加不同效果）
     * 武器：添加套装效果 （武器宝石提供不同效果）
     * </pre>
     */
    private AbstractItem item;
    /**
     * 是否永久效果
     */
    private boolean permanent;
    /**
     * 是否已生效
     */
    private boolean hasApplied;
    /**
     * 持续回合
     */
    private int duration;
    /**
     * 描述
     */
    private String description;
}
