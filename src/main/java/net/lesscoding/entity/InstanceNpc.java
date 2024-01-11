package net.lesscoding.entity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @TableField(exist = false)
    private List<GameProps> dropPropsList;

    @TableField(exist = false)
    private String dropPropsStr;

    public String getDropPropsStr() {
        if (CollUtil.isEmpty(dropPropsList)) {
            return "";
        }
        return dropPropsList.stream()
                .map(item -> StrUtil.format("道具: {}\n掉落概率: {}%\n描述{}\n",
                        item.getPropsName(),
                        BigDecimal.valueOf(item.getDropProbability() * 100).doubleValue(),
                        item.getPropsIntro()
                        ))
                .collect(Collectors.joining(","));

    }
}
