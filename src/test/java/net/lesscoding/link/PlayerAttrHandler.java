package net.lesscoding.link;

import net.lesscoding.entity.HandlerRequest;
import net.lesscoding.enums.BattleStatus;

/**
 * @author eleven
 * @date 2024/3/12 10:23
 * @apiNote
 */
public class PlayerAttrHandler extends BattleHandler {

    @Override
    public void handle(HandlerRequest request) {
        if (BattleStatus.PLAYER_ATTR_SET.equals(request.getState())) {

        }
        nextHandler.handle(request);
    }
}
