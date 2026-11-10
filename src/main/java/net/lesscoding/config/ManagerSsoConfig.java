package net.lesscoding.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * manager SSO 共享 Redis 连接配置。
 *
 * 方案A（单机最合适）：游戏后端与 xechat-manager 共用同一 Redis 实例，
 * 通过读 manager 的 Sa-Token 会话映射 key 直接解析登录态，拿到 userId，
 * 无需开放 manager 业务接口、无需引入 sa-token 依赖。
 *
 * 默认值对应 xechat-manager dev 配置（localhost:6379 / db0 / token-name=satoken），
 * 单机部署可直接使用；非单机时通过 xechat.sso.manager.redis.* 覆盖为 manager 的 Redis 地址。
 */
@Configuration
public class ManagerSsoConfig {

    @Value("${xechat.sso.manager.redis.host:localhost}")
    private String host;

    @Value("${xechat.sso.manager.redis.port:6379}")
    private int port;

    @Value("${xechat.sso.manager.redis.password:}")
    private String password;

    @Value("${xechat.sso.manager.redis.database:0}")
    private int database;

    @Bean("managerRedisTemplate")
    public StringRedisTemplate managerRedisTemplate() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setDatabase(database);
        if (StrUtil.isNotBlank(password)) {
            config.setPassword(RedisPassword.of(password));
        }
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config);
        factory.afterPropertiesSet();
        return new StringRedisTemplate(factory);
    }
}
