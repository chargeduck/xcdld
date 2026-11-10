package net.lesscoding.model.dto;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/8 12:15
 * @apiNote
 */
@Data
public class AccountDto {

    /**
     * 登录类型
     * 0 使用网卡mac地址登录
     * 1 使用 account 系统生成账号登录
     * 3 使用邮箱验证码登录
     */
    private Integer type;

    /**
     * 网卡地址
     */
    private String mac;

    /**
     * 账号
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    private String region;

    private String nickname;

    /**
     * manager 平台登录态 token（SSO 登录时传入，来自 xechat-manager 的 satoken）
     */
    private String satoken;

}
