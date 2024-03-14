package net.lesscoding.link;

import net.lesscoding.entity.HandlerRequest;
import net.lesscoding.enums.BattleStatus;

/**
 * @author eleven
 * @date 2024/3/12 17:01
 * @apiNote
 */
public class InitEnvHandler extends BattleHandler{
    @Override
    public void handle(HandlerRequest request) {
        System.out.println("初始化进城");
        request.setState(BattleStatus.PLAYER_ATTR_SET);
        nextHandler.handle(request);
    }
}
