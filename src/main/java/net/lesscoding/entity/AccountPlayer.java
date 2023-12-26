package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/10 16:43
 * @apiNote
 */
@Data
@TableName("tb_account_player")
public class AccountPlayer extends BaseEntity {
    /**
     *  账号id
     */
    private Integer accountId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 当前等级
     */
    private Integer level;

    /**
     * 当前经验值
     */
    private Integer exp;

    private Integer energy;

    private Integer gold;
}
