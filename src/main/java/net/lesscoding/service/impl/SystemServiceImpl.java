package net.lesscoding.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.model.vo.PlayerInfoVo;
import net.lesscoding.service.AccountPlayerService;
import net.lesscoding.service.AccountService;
import net.lesscoding.service.SystemService;
import net.lesscoding.utils.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/8 12:14
 * @apiNote
 */
@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountPlayerService playerService;

    @Autowired
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate jedisTemplate;

    @Value("${redis.userCache}")
    private String userCache;

    @Autowired
    private Gson gson;

    @Autowired
    private AccountUtil accountUtil;

    @Override
    public Object registerAccount(AccountDto dto) {
        return accountService.registerAccount(dto);
    }

    @Override
    public Object doLogin(AccountDto dto) {
        String token = accountService.doLogin(dto);
        PlayerInfoVo playerDetail = playerService.getPlayerDetail(null);
        playerDetail.setToken(token);
        return playerDetail;
    }

    @Override
    public Object ssoLogin(AccountDto dto) {
        String token = accountService.ssoLogin(dto);
        PlayerInfoVo playerDetail = playerService.getPlayerDetail(null);
        playerDetail.setToken(token);
        return playerDetail;
    }

    @Override
    public String redeploy() {

        return null;
    }

}
