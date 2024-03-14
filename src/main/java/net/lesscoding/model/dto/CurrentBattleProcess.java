package net.lesscoding.model.dto;

import lombok.*;
import net.lesscoding.entity.Weapon;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CurrentBattleProcess {

    private String originAttackerName;

    private String originDefenderName;

    private String attackerName;

    private String defenderName;

    private int round;

    /**
     * 攻击者基础攻击力
     */
    private Integer attackerAttack;

    /**
     * 防御者hp
     */
    private Integer defenderHp;

    /**
     * 防御者防御值
     */
    private Integer defenderDefence;

    /**
     * 防御者基础攻击力，预留，为后续防御者反击做准备
     */
    private Integer defenderAttack;

    /**
     * 攻击者hp，预留，为后续防御者反击做准备
     */
    private Integer attackerHp;

    /**
     * 攻击者防御值，预留，为后续防御者反击做准备
     */
    private Integer attackerDefence;

    /**
     * 攻击者武器列表
     */
    private List<Weapon> attackerWeaponList;

    /**
     * 防御者闪避率
     */
    private Double flee;

    /**
     * 攻击者暴击率
     */
    private Double criticalChance;

    /**
     * 防御者flag
     */
    private Boolean fleeFlag;

    /**
     * 攻击者flag
     */
    private Boolean criticalFlag;

    /**
     * 当前选中的武器
     */
    private Weapon currentWeapon;

    /**
     * 武器攻击buff
     */
    private WeaponBuff weaponBuff;

    /**
     * 计算后的实际攻击值
     */
    private Integer calAttack;

    /**
     * 当前回合状态,0：没结束,1：防御者HP<=0，2:攻击者HP<=0,3：同时为0
     */
    private Integer currentState;

    /**
     * 当前回合结果
     */
    private StringBuilder currentResult;
}
