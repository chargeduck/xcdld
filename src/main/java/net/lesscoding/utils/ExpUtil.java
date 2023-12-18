package net.lesscoding.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.common.Consts;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.vo.fight.BattleProcess;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.AddExpDto;
import net.lesscoding.model.vo.AfterPlayerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/10/31 11:22
 * @apiNote
 */
@Component
@Slf4j
public class ExpUtil {

    @Autowired
    private AccountPlayerMapper playerMapper;

    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;

    @Autowired
    @Qualifier("intRedisTemplate")
    private RedisTemplate intRedisTemplate;

    /**
     * 添加玩家的经验
     * @param attacker          攻击者
     * @param defender          防御者
     * @param processList       进程集合
     * v1： winnerExp
     *
     */
    public void addPlayerExp(Player attacker, Player defender, List<BattleProcess> processList) {
        if (!hasEnergy(attacker.getId())) {
            processList.add(new BattleProcess(0, "体力值不足，请使用道具恢复或等待整点自动恢复"));
            return;
        }
        List<Player> playerList = new ArrayList<>();
        Collections.addAll(playerList, attacker, defender);
        playerList.sort(Comparator.comparingInt(Player::getHp));
        Player loser = playerList.get(0);
        Player winner = playerList.get(1);
        // 等级差
        int winnerLv = winner.getLevel();
        int loserLv = loser.getLevel();
        int lvDiff = Math.abs(winnerLv - loserLv);
        int lvExp = lvDiff + 1;
        int hpExpRatio = Math.max(2, lvExp * lvExp);
        int hpExp = 1;
        log.info("当前胜利者 {} 等级 {}", winner.getNickname(), winnerLv);
        log.info("当前失败者 {} 等级 {}", loser.getNickname(), loserLv);
        log.info("当前胜利者剩余HP {}", winner.getHp());
        log.info("等级差：：{}，hp比例：：{}", lvDiff, hpExpRatio);
        int realLvExp = lvDiff > 5 ? 1 : lvExp;
        log.info("等级差经验为 {}, 公式： 等级差 > 5 ? 1 : 等级差 + 1 ", realLvExp);
        String formatStr = "当前计算公式 %d + %d %s %d + 1 = %d";
        String hpExpLogStr = "";
        boolean vsHighLevel = winnerLv < loserLv;
        if (vsHighLevel) {
            hpExp = winner.getHp() * Math.max(2, lvExp);
        } else {
            hpExp = winner.getHp() / hpExpRatio;
        }
        hpExpLogStr = String.format(formatStr,
                realLvExp,
                winner.getHp(), vsHighLevel ? "*" : "/" , hpExpRatio,
                hpExp);
        log.info(hpExpLogStr);
        int winnerExp = realLvExp + hpExp + 1;
        int loserExp = Math.max(lvDiff * lvDiff, winnerExp / 5  / lvExp);
        /**
         * 被动战斗还赢了
         */
        if (winner.getId().equals(defender.getId())) {
            winnerExp /= lvExp;
        }
        log.info("胜利者获得 {} exp， 失败者获得 {} exp", winnerExp, loserExp);
        playerMapper.addPlayerExp(new AddExpDto(winner.getId(), winnerExp, winner.getIsNpc()));
        playerMapper.addPlayerExp(new AddExpDto(loser.getId(), loserExp, loser.getIsNpc()));
        // 扣减玩家体力值
        subEnergy(attacker.getId());
        processList.add(new BattleProcess(processList.size() + 1,
                String.format("你%s", attacker.getHp() > 0 ? "挑战成功" : "挑战失败")));
        processList.add(new BattleProcess(processList.size() + 1,
                String.format("你获得了%d经验值", attacker.getHp() > 0 ? winnerExp : loserExp)));
        List<AfterPlayerVo> afterList = playerMapper.selectAfterPlayer(Arrays.asList(attacker.getId(), defender.getId()));
        addWeapon(attacker, defender, processList, afterList);
    }

    private boolean hasEnergy(Integer id) {
        String energyKey = energyKey(id);
        String energyStr = String.valueOf(intRedisTemplate.opsForValue().get(energyKey));
        // redis不存在key值 将key更新
        if (StrUtil.isBlank(energyStr) || StrUtil.equalsIgnoreCase("null", energyStr)) {
            AccountPlayer player = playerMapper.selectById(id);
            intRedisTemplate.opsForValue().set(energyKey, player.getEnergy());
            return true;
        }
        return Integer.parseInt(energyStr) > 0;
    }

    /**
     * 扣减玩家的体力值
     * @param id
     */
    private void subEnergy(Integer id) {
        String energyKey = energyKey(id);
        log.info("当前体力key为 {}", energyKey);
        playerMapper.subEnergy(id, Consts.INT_STATE_1);
        String energyStr = String.valueOf(intRedisTemplate.opsForValue().get(energyKey));
        // redis不存在key值 将key更新
        if (StrUtil.isBlank(energyStr) || StrUtil.equalsIgnoreCase("null", energyStr)) {
            AccountPlayer player = playerMapper.selectById(id);
            intRedisTemplate.opsForValue().set(energyKey, player.getEnergy());
        } else {
            // 否则就直接 -1
            intRedisTemplate.opsForValue().decrement(energyKey);
        }
    }

    private String energyKey(Integer id) {
        return String.format("energy:%d", id);
    }


    /**
     * 添加获取武器的代码
     * @param attacker          攻击者
     * @param defender          防御者
     * @param processList       进程列表
     * @param afterList         变更之后的玩家列表
     */
    private void addWeapon(Player attacker, Player defender,
                           List<BattleProcess> processList, List<AfterPlayerVo> afterList) {
        Map<Integer, AfterPlayerVo> playerVoMap = afterList.stream()
                .collect(Collectors.toMap(AfterPlayerVo::getId, Function.identity()));
        List<PlayerWeapon> insertList = new ArrayList<>(2);
        addPlayerWeapon(playerVoMap, attacker, insertList, processList, true);
        if (!defender.getIsNpc()) {
            addPlayerWeapon(playerVoMap, defender, insertList, processList, false);
        }
        // TODO: 2023/12/16 如果玩家已经有这个武器的话，就不应该继续添加了
        if (CollUtil.isNotEmpty(insertList)) {
            List<PlayerWeapon> queryDataList = playerWeaponMapper.selectList(new QueryWrapper<PlayerWeapon>()
                    .in("player_id", attacker.getId(), defender.getId()));
            Map<Integer, List<PlayerWeapon>> playerWeaponsMap = queryDataList.stream()
                    .collect(Collectors.groupingBy(PlayerWeapon::getPlayerId));
            insertList = insertList.stream()
                            .filter(item -> {
                                List<PlayerWeapon> playerWeapons = playerWeaponsMap.get(item.getPlayerId());
                                if (CollUtil.isEmpty(playerWeapons)) {
                                    return true;
                                }
                                List<Integer> weaponIds = playerWeapons.stream()
                                        .map(PlayerWeapon::getWeaponId)
                                        .collect(Collectors.toList());
                                return !weaponIds.contains(item.getWeaponId());
                            })
                            .collect(Collectors.toList());
            if (CollUtil.isNotEmpty(insertList)) {
                playerWeaponMapper.insertBatch(insertList);
            }
        }
    }

    /**
     * 添加武器
     * @param playerVoMap
     * @param player
     * @param insertList
     * @param processList
     * @param addFlag
     */
    private void addPlayerWeapon(Map<Integer, AfterPlayerVo> playerVoMap, Player player,
                                 List<PlayerWeapon> insertList, List<BattleProcess> processList,
                                 Boolean addFlag) {
        AfterPlayerVo afterAttacker = playerVoMap.get(player.getId());
        List<PlayerWeapon> playerWeaponList = new ArrayList<>(2);
        if (afterAttacker.getLevel() > player.getLevel()) {
            if (addFlag) {
                processList.add(new BattleProcess(processList.size() + 2,
                        String.format("你升级了，当前%d级", afterAttacker.getLevel())));
            }
            Weapon weapon = afterAttacker.getWeapon();
            if (weapon != null) {
                log.info("当前玩家 {} 获得武器 {}", player, weapon);
                if (weapon.getId() != null) {
                    insertList.add(new PlayerWeapon(player.getId(), weapon.getId(), 0));
                }
                playerWeaponList.add(new PlayerWeapon(player.getId(), weapon.getId(), 0));
                if (addFlag) {
                    processList.add(new BattleProcess(processList.size() + 3,
                            String.format("你获得了【%s】描述【%s】攻击力[%d ~ %d]",
                                    weapon.getName(),
                                    weapon.getIntro(),
                                    weapon.getMinDamage(),
                                    weapon.getMaxDamage()))
                    );
                }
            }
        }
    }
}
