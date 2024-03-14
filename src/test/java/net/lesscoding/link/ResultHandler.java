package net.lesscoding.link;

import net.lesscoding.entity.HandlerRequest;
import net.lesscoding.enums.BattleStatus;


/**
 * @author eleven
 * @date 2024/3/12 17:08
 * @apiNote
 */
public class ResultHandler extends BattleHandler{
    @Override
    public void handle(HandlerRequest request) {
        if (request.getState().equals(BattleStatus.END)) {
            return;
        }
        nextHandler.handle(request);
    }
}
