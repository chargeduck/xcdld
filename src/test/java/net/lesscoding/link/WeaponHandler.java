package net.lesscoding.link;

import net.lesscoding.entity.HandlerRequest;
import net.lesscoding.enums.BattleStatus;

/**
 * @author eleven
 * @date 2024/3/12 16:57
 * @apiNote
 */
public class WeaponHandler extends BattleHandler{
    @Override
    public void handle(HandlerRequest request) {
        if (BattleStatus.WEAPON_SET.equals(request.getState())) {

        }
        nextHandler.handle(request);
    }
}
