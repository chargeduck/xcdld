package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lesscoding.common.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * @author eleven
 * @date 2023/12/15 15:25
 * @apiNote 词条数据
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_entry_item")
public class EntryItem extends BaseEntity<EntryItem> {

    /**
     * 词条名称
     */
    @NotNull(message = "词条名称不能为空")
    private String entryName;

    /**
     * 描述
     */
    private String intro;

    @TableField(exist = false)
    private Page page;
}
