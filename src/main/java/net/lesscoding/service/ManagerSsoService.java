package net.lesscoding.service;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * manager SSO 登录态解析服务。
 *
 * 原理：xechat-manager 使用 Sa-Token 登录后，会在其 Redis 写入
 * key = {token-name}:login:token:{tokenValue}，value = userId 字符串。
 * 游戏后端共享该 Redis（单机同一实例 / 多机同一地址），
 * 只需 GET 该 key 即可校验登录态并拿到 userId，零依赖、零接口调用。
 *
 * 说明：key 前缀中的 {token-name} 即 manager 的 sa-token.token-name（当前为 satoken），
 * 若 manager 侧调整 token-name，此处通过 xechat.sso.manager.token-name 同步覆盖。
 */
@Slf4j
@Service
public class ManagerSsoService {

    private final StringRedisTemplate managerRedisTemplate;

    @Value("${xechat.sso.manager.token-name:satoken}")
    private String tokenName;

    public ManagerSsoService(@Qualifier("managerRedisTemplate") StringRedisTemplate managerRedisTemplate) {
        this.managerRedisTemplate = managerRedisTemplate;
    }

    /**
     * 校验 manager 登录态并返回 userId；token 无效或已过期返回 null。
     */
    public Long getManagerUserId(String satoken) {
        if (StrUtil.isBlank(satoken)) {
            return null;
        }
        try {
            String key = tokenName + ":login:token:" + satoken;
            String userId = managerRedisTemplate.opsForValue().get(key);
            if (StrUtil.isBlank(userId)) {
                return null;
            }
            return Long.valueOf(userId);
        } catch (Exception e) {
            log.warn("manager sso parse failed: {}", e.getMessage());
            return null;
        }
    }
}
