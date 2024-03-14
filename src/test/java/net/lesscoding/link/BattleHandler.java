package net.lesscoding.link;

import net.lesscoding.entity.HandlerRequest;

/**
 * @author eleven
 * @date 2024/3/12 10:10
 * @apiNote
 */

public abstract class BattleHandler {

    public BattleHandler nextHandler = null;

    public BattleHandler setNextHandler(BattleHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this;
    }
    public abstract void handle(HandlerRequest request);


}
