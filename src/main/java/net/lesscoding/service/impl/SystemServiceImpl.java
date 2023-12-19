package net.lesscoding.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.RandomUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.Account;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.model.vo.PlayerInfoVo;
import net.lesscoding.model.vo.RedisUserCache;
import net.lesscoding.service.AccountPlayerService;
import net.lesscoding.service.AccountService;
import net.lesscoding.service.SystemService;
import net.lesscoding.utils.AccountUtil;
import net.lesscoding.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Scheduled(cron = "0 0/15 * * * ?")
    public Integer autoRegisterByRedisMac() {
        List<Account> accountList = accountService.getAccountList();
        Map<String,String> userCacheMap = jedisTemplate.boundHashOps(userCache).entries();
        Set<String> redisMacSet = userCacheMap.keySet();
        if (CollUtil.isNotEmpty(accountList)) {
            List<String> existsMacList = accountList.stream()
                    .map(Account::getMac)
                    .collect(Collectors.toList());
            // 获取到没有注册的mac地址
            existsMacList.forEach(redisMacSet::remove);
        }
        List<Account> insertList = new ArrayList<>();
        Set<String> accountSet = accountList.stream()
                .map(Account::getAccount)
                .collect(Collectors.toSet());
        List<String> randomAccountList = accountUtil.getAccountList(accountSet, redisMacSet.size());
        for (String redisMac : redisMacSet) {
            RedisUserCache userVo = gson.fromJson(userCacheMap.get(redisMac), RedisUserCache.class);
            Account account = new Account(userVo);
            String randomAccount = RandomUtil.randomEle(randomAccountList);
            randomAccountList.remove(randomAccount);
            account.setAccount(randomAccount);
            account.setPassword(PasswordUtil.encrypt(account.getAccount(), account.getSalt()));
            insertList.add(account);
        }

        if (CollUtil.isNotEmpty(insertList)) {
            log.info("当前要自动创建账号数量为 -{}", insertList.size());
            TimeInterval timer = DateUtil.timer();
            accountService.saveBatch(insertList, 500);
            playerService.addPlayerByAccount(insertList);
            log.info("当前创建账号完成 用时{}", timer.interval());
        }
        return redisMacSet.size();
    }

    @Override
    public Object doLogin(AccountDto dto) {
        String token = accountService.doLogin(dto);
        PlayerInfoVo playerDetail = playerService.getPlayerDetail(null);
        playerDetail.setToken(token);
        return playerDetail;
    }

    @Override
    public String redeploy() {

        return null;
    }

}
