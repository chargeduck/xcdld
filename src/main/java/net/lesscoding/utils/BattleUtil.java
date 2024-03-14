package net.lesscoding.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import net.lesscoding.model.vo.fight.BattleProcess;
import net.lesscoding.entity.Weapon;
import net.lesscoding.link.*;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.CurrentBattleProcess;
import net.lesscoding.model.vo.SkillVo;

import java.util.*;

/**
 * @author eleven
 * @date 2023/9/22 15:58
 * @apiNote 战斗工具类
 */
public class BattleUtil {

    /**
     * 战斗工具类
     * @param attacker      攻击者
     * @param defender      被攻击者
     * @param processList   进程集合
     * @param round         回合数
     */
    public static void doBattle(Player attacker, Player defender, List<BattleProcess> processList, int round) {
        while(true) {
            if(attacker.getHp() <= 0 || defender.getHp() <= 0) {
                break;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("【第%d回合】", round));
            int attack = attacker.getAttack();
            // 是否使用武器
            Boolean useWeaponFlag = getWeightResult(0.5);
            if (useWeaponFlag && CollUtil.isNotEmpty(attacker.getWeaponList())) {
                Weapon weapon = RandomUtil.randomEle(attacker.getWeaponList());
                sb.append(String.format("玩家[%s]使用了武器[%s],", attacker.getNickname(), weapon.getName()));
                Integer rangeAttack = getRangeAttack(weapon.getMinDamage(), weapon.getMaxDamage());
                attack += rangeAttack;
                sb.append(String.format("攻击力提升了%d,当前%d,", rangeAttack, attack));
            }
            Boolean useSkillFlag = getWeightResult(0.5);
            List<SkillVo> skillList = attacker.getSkillList();
            if (useSkillFlag && CollUtil.isNotEmpty(skillList)) {
                SkillVo skillVo = RandomUtil.randomEle(skillList);
                sb.append(String.format("玩家[%s]使用了技能[%s],", attacker.getNickname(), skillVo.getName()));
                Integer rangeAttack = getRangeAttack(skillVo.getMinDamage(), skillVo.getMaxDamage());
                attack += rangeAttack;
                sb.append(String.format("攻击力提升了%d,当前%d,", rangeAttack, attack));
            }

            Boolean fleeFlag = getWeightResult(defender.getFlee());
            if (fleeFlag) {
                sb.append(String.format("%s侧身一躲，躲过了%s的致命一击", defender.getNickname(), attacker.getNickname()));
            } else {
                attack -= defender.getDefence();
                Boolean critical = getWeightResult(defender.getCriticalChance());
                if (critical) {
                    attack *= 2;
                }
                // 防止攻击为负数导致被攻击者加血
                attack = Math.max(0, attack);
                defender.setHp(Math.max(0, defender.getHp() - attack));
                sb.append(String.format("%s %s被击中, HP减少 %d, 当前 %d",
                        critical ? "触发暴击，当前伤害 * 2" : "" ,
                        defender.getNickname(),
                        attack,
                        defender.getHp()));
            }
            round++;
            processList.add(new BattleProcess(round, sb.toString()));
            Boolean comboFlag = getWeightResult(attacker.getComboRate());
            if (comboFlag) {
                doBattle(attacker, defender, processList, round);
            } else {
                doBattle(defender, attacker, processList, round);
            }
        }
    }


    /**
     * 战斗工具类
     *
     * @param attacker    攻击者
     * @param defender    被攻击者
     * @param processList 进程集合
     * @param round       回合数
     */
    public static void doBattleByPlugIn(Player attacker, Player defender, List<BattleProcess> processList, int round) {
        while (attacker.getHp() > 0 && defender.getHp() > 0) {
            BattleRequestHandler battleRequestHandler = initBattleHandler();
            BattleRequest battleRequest = initBattleRequest(attacker, defender, round);
            battleRequestHandler.handleRequest(battleRequest);
            CurrentBattleProcess currentBattleProcess = battleRequest.getCurrentBattleProcess();
            Integer currentState = currentBattleProcess.getCurrentState();
            attacker.setHp(currentBattleProcess.getAttackerHp());
            defender.setHp(currentBattleProcess.getDefenderHp());
            processList.add(new BattleProcess(round, currentBattleProcess.getCurrentResult().toString()));
            if (currentState != 0) {
                doBattle(defender, attacker, processList, round);
            } else {
                break;
            }
        }
    }

    /**
     * 初始化责任链
     * @return
     */
    public static BattleRequestHandler initBattleHandler() {
        BattleRequestHandler initBattleEnvHandler = new InitBattleEnvHandler();
        BattleRequestHandler playerAttributeHandler = new PlayerAttributeHandler();
        BattleRequestHandler weaponAttributeHandler = new WeaponAttributeHandler();
        BattleRequestHandler playerSkillHandler = new PlayerSkillHandler();
        BattleRequestHandler resultHandler = new ResultHandler();

        initBattleEnvHandler.setNextHandler(playerAttributeHandler);
        playerAttributeHandler.setNextHandler(weaponAttributeHandler);
        weaponAttributeHandler.setNextHandler(playerSkillHandler);
        playerSkillHandler.setNextHandler(resultHandler);

        return initBattleEnvHandler;
    }

    public static BattleRequest initBattleRequest(Player attacker, Player defender, int round) {
        CurrentBattleProcess currentBattleProcess = CurrentBattleProcess.builder()
                .originAttackerName(attacker.getNickname())
                .attackerName(attacker.getNickname())
                .attackerDefence(attacker.getDefence())
                .attackerAttack(attacker.getAttack())
                .attackerHp(attacker.getHp())
                .attackerWeaponList(attacker.getWeaponList())
                .originDefenderName(defender.getNickname())
                .defenderAttack(defender.getAttack())
                .defenderName(defender.getNickname())
                .defenderDefence(defender.getDefence())
                .defenderHp(defender.getHp())
                .flee(defender.getFlee())
                .criticalChance(attacker.getCriticalChance())
                .round(round).build();
        return BattleRequest.builder().currentBattleProcess(currentBattleProcess).build();
    }

   /**
     * 坚果教主提供的战斗工具类
     * @author 坚果教主
     * @param attacker  攻击者
     * @param defender  防御者
     * @return  List    战斗过程
     */
    public static List<Map<String, Object>> fight(Player attacker, Player defender) {
        List<Weapon> attackerWeaponList = attacker.getWeaponList();
        List<Weapon> defenderWeaponList = defender.getWeaponList();

        Double attackerComboRate = attacker.getComboRate();
        Double defenderComboRate = defender.getComboRate();

        Double attackerFlee = attacker.getFlee();
        Double defenderFlee = defender.getFlee();

        String attackerNickname = attacker.getNickname();
        String defenderNickname = defender.getNickname();

        Integer attackerHp = attacker.getHp();
        Integer defenderHp = defender.getHp();

        Integer attackerDefence = attacker.getDefence();
        Integer defenderDefence = defender.getDefence();

        Random random = new Random();

        List<Map<String, Object>> processList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Map<String, Object> singleRound = new HashMap<>();

            Boolean attackerComboRateFlag = getWeightResult(attackerComboRate);
            Boolean defenderComboRateFlag = getWeightResult(defenderComboRate);
            Boolean attackerFleeFlag = getWeightResult(attackerFlee);
            Boolean defenderFleeFlag = getWeightResult(defenderFlee);

            int attackerWeaponIndex = random.nextInt(attackerWeaponList.size() + 1);
            int defenderWeaponIndex = random.nextInt(defenderWeaponList.size() + 1);

            Weapon attackWeapon = attackerWeaponList.get(attackerWeaponIndex);
            Weapon defenderWeapon = attackerWeaponList.get(defenderWeaponIndex);

            String attackerWeaponName = attackWeapon.getName();
            String defenderWeaponName = defenderWeapon.getName();

            singleRound.put("attacker", attackerNickname);
            singleRound.put("attackerWeaponName", attackerWeaponName);
            singleRound.put("defender", defenderNickname);
            singleRound.put("defenderWeaponName", defenderWeaponName);

            int attackerPower = 0;
            int defenderPower = 0;

            if (!attackerFleeFlag) {
                if (attackerComboRateFlag) {
                    attackerPower = getRangeAttack(attackWeapon.getMinDamage(), attackWeapon.getMaxDamage()) + getRangeAttack(attackWeapon.getMinDamage(), attackWeapon.getMaxDamage()) - 2 * defenderDefence;
                } else {
                    attackerPower = getRangeAttack(attackWeapon.getMinDamage(), attackWeapon.getMaxDamage()) - defenderDefence;
                }
            }

            if (!defenderFleeFlag) {
                if (defenderComboRateFlag) {
                    defenderPower = getRangeAttack(defenderWeapon.getMinDamage(), defenderWeapon.getMaxDamage()) + getRangeAttack(defenderWeapon.getMinDamage(), defenderWeapon.getMaxDamage()) - 2 * attackerDefence;
                } else {
                    defenderPower = getRangeAttack(defenderWeapon.getMinDamage(), defenderWeapon.getMaxDamage()) - attackerDefence;
                }
            }
            singleRound.put("attackerPower", attackerPower);
            singleRound.put("defenderPower", defenderPower);
            attackerHp -= defenderPower;
            defenderHp -= attackerPower;
            processList.add(singleRound);
            if (attackerHp <= 0 || defenderHp <= 0) {
                break;
            }
        }
        return processList;
    }

    /**
     * 获取区间数值 [min,max] 区间的数值
     * @param min   最小值(包含)
     * @param max   最大值(不包含)
     * @return Integer 最终数据
     */
    public static Integer getRangeAttack(Integer min, Integer max) {
        return RandomUtil.randomInt(min - 1, max + 1);
    }

    /**
     * 权重随机数
     * @param weight        权重值
     * @return Boolean
     */
    public static Boolean getWeightResult(double weight) {
        if(weight < 0) {
            weight = 0;
        }
        if(weight > 1) {
            weight = 1;
        }
        ArrayList<WeightRandom.WeightObj<Boolean>> list = Lists.newArrayList(
                new WeightRandom.WeightObj<>(true, weight),
                new WeightRandom.WeightObj<>(false, 1 - weight)
        );
        return RandomUtil.weightRandom(list).next();
    }

    public static String getFightResult(Player attacker, Player defender) {
        Integer attackerHp = attacker.getHp();
        StringBuffer sb = new StringBuffer();
        if (attackerHp > 0) {
            sb.append("你击败了【")
                    .append(defender.getNickname())
                    .append("】获得了胜利。评价：")
                    .append(attackerHp < 10 ? "惨胜" : "完胜");
        } else {
            sb.append("惜败！");
        }
        return sb.toString();
    }

}
