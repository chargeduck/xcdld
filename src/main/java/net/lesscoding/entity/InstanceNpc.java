package net.lesscoding.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author eleven
 * @date 2023/10/19 16:06
 * @apiNote
 */
@Data
@TableName("tb_instance_npc")
public class InstanceNpc extends BaseEntity {
    private Integer instanceId;

    private String npcName;

    private Integer playerId;

    private Boolean bossFlag;

    private Double increaseRatio;

    private Integer floor;

    private String dropIds;

    @TableField(exist = false)
    private Boolean defeatFlag;

    @TableField(exist = false)
    private List<String> dropIdList;

    public List<String> getDropIdList() {
        if (StrUtil.isNotBlank(dropIds)) {
            return Arrays.asList(dropIds.split(","));
        }
        return new ArrayList<>();
    }
}
