package net.lesscoding.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2024/1/4 17:08
 * @apiNote
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerSkill extends BaseEntity {
    private Integer playerId;

    private Integer skillId;

    private Integer skillLevel;
}
