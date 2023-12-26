package net.lesscoding.runner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.mapper.PlayerWeaponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author eleven
 * @date 2023/12/26 15:28
 * @apiNote
 */
@Component
@Slf4j
public class DiscardRunner implements ApplicationRunner {

    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;

    @Override
    public void run(ApplicationArguments args) {
        int deleteWeapon = playerWeaponMapper.delete(new QueryWrapper<PlayerWeapon>()
                .eq("del_flag", true)
                .le("create_time", LocalDateTime.now().minusDays(180))
        );
        log.info("删除过期武器数据 {} 条", deleteWeapon);
    }
}
