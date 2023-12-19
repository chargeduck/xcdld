package net.lesscoding.service;

import net.lesscoding.model.dto.AccountDto;

/**
 * @author eleven
 * @date 2023/10/8 12:13
 * @apiNote
 */
public interface SystemService {

    /**
     * 注册账号
     * @param dto   注册的用户
     * @return  String
     */
    Object registerAccount(AccountDto dto);

    /**
     * 登陆接口
     * @param dto       登录参数
     * @return
     */
    Object doLogin(AccountDto dto);

    String redeploy();
}
