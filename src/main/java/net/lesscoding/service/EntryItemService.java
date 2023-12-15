package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.EntryItem;

/**
 * @author eleven
 * @date 2023/12/15 15:38
 * @apiNote
 */
public interface EntryItemService extends IService<EntryItem> {
    /**
     * 编辑词条数据
     * @param entryItem 词条数据
     * @return Integer  编辑数据
     */
    Integer editEntryItem(EntryItem entryItem);

    Page<EntryItem> getEntryItemPage(EntryItem entryItem);
}
