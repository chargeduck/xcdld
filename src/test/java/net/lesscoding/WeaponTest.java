package net.lesscoding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.mapper.WeaponMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/10/23 8:34
 * @apiNote
 */
@SpringBootTest(classes = MainApp.class)
@RunWith(SpringRunner.class)
@RefreshScope
@Slf4j
public class WeaponTest {

    @Autowired
    private WeaponMapper weaponMapper;

    @Autowired
    private AccountPlayerMapper playerMapper;

    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;

    @Test
    public void initDamage() {
        List<Weapon> weapons = weaponMapper.selectList(new QueryWrapper<Weapon>().orderByAsc("id"));
        for (Weapon weapon : weapons) {
            weapon.setMinDamage(2 * weapon.getId() + 1);
            weapon.setMaxDamage(weapon.getMinDamage() + 2 * (weapon.getId() * weapon.getId()));
            weaponMapper.updateById(weapon);
        }
    }

    @Test
    public void initPlayerWeapon() {
        List<AccountPlayer> accountPlayerList = playerMapper.selectList(null);
        List<PlayerWeapon> insertList = new ArrayList<>(accountPlayerList.size());
        PlayerWeapon playerWeapon = null;
        for (AccountPlayer accountPlayer : accountPlayerList) {
            playerWeapon = new PlayerWeapon();
            playerWeapon.setPlayerId(accountPlayer.getId());
            playerWeapon.setWeaponId(0);
            playerWeapon.setAddLevel(0);
            insertList.add(playerWeapon);
        }
        System.out.println(playerWeaponMapper.insertBatch(insertList));
    }

    @Test
    public void delDuplicateData() {
        List<PlayerWeapon> playerWeapons = playerWeaponMapper.selectList(null);
        Map<Integer, List<PlayerWeapon>> playerWeaponsMap = playerWeapons.stream()
                .collect(Collectors.groupingBy(PlayerWeapon::getPlayerId));
        List<PlayerWeapon> delDataList = new ArrayList<>(playerWeapons.size());
        playerWeaponsMap.forEach((key, value) -> {
            Map<Integer, List<PlayerWeapon>> weaponMap = value.stream()
                    .collect(Collectors.groupingBy(PlayerWeapon::getWeaponId));
            weaponMap.forEach((weaponId, data) -> {
                if (data.size() > 1) {
                    data.remove(0);
                    delDataList.addAll(data);
                }
            });
        });
        List<Integer> delPlayerWeaponIds = delDataList.stream()
                .map(PlayerWeapon::getId)
                .collect(Collectors.toList());
        int delRows = playerWeaponMapper.delete(new QueryWrapper<PlayerWeapon>()
                .in("id", delPlayerWeaponIds));
        log.info("删除 {} 条数据", delRows);
    }
}
