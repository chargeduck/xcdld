package net.lesscoding.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/3/21 15:38
 * @description
 */
@Data
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * dict类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 编码
     */
    private String dictCode;

    /**
     * 值
     */
    private String dictValue;

    /**
     * 描述
     */
    private String description;
}
