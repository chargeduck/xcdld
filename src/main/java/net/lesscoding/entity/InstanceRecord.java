package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

import java.time.LocalDate;

/**
 * @author eleven
 * @date 2023/10/31 8:31
 * @apiNote
 */
@Data
@TableName("tb_instance_record")
public class InstanceRecord extends BaseEntity {
    /**
     * 玩家id
     */
    private Integer playerId;

    /**
     * 副本id
     */
    private Integer instanceId;

    /**
     *
     */
    private LocalDate challengeDate;

    /**
     * 当前层数
     */
    private Integer currentFloor;

    /**
     * 是否完成
     */
    private Boolean completeFlag;

    private Boolean successFlag;
}
