package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/31 9:44
 * @apiNote
 */
@Data
@TableName("tb_player_package")
public class PlayerPackage extends BaseEntity {

    /**
     * 玩家id
     */
    private Integer playerId;

    /**
     * 数量
     */
    private Integer num;

    /**
     * id
     */
    private Integer objId;
}
