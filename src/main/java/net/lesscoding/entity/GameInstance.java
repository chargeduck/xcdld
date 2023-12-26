package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/19 16:03
 * @apiNote
 */
@Data
@TableName("tb_game_instance")
public class GameInstance extends BaseEntity {
    /**
     * 副本名称
     */
    private String instanceName;

    /**
     * 副本描述
     */
    private String instanceInfo;

    /**
     * 层数
     */
    private Integer floorNum;

    /**
     * 准入等级
     */
    private Integer accessLevel;
}
