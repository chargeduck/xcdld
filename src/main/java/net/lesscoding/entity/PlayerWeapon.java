package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/23 9:05
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_player_weapon")
public class PlayerWeapon extends BaseEntity {
    private Integer playerId;

    private Integer weaponId;

    private Integer addLevel;
}
