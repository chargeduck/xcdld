package net.lesscoding.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/12/26 14:29
 * @apiNote
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscardLog extends BaseEntity {

    private Integer objId;

    private Integer type;

    private Integer playerId;

    private Integer discardType;

    private String reason;
}
