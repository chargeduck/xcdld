package net.lesscoding.entity;

import lombok.Data;
import net.lesscoding.enums.BattleStatus;
import net.lesscoding.model.dto.PlayerDto;

/**
 * @author eleven
 * @date 2024/3/12 10:24
 * @apiNote
 */
@Data
public class HandlerRequest {

    private BattleStatus state;

    private PlayerDto attacker;

    private PlayerDto defender;
    /**
     * 原始攻击者
     */
    private PlayerDto originAttacker;
    /**
     * 原始防御者
     */

    private PlayerDto originDefender;
}
