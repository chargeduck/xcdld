package net.lesscoding.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.EntryItem;
import net.lesscoding.mapper.EntryItemMapper;
import net.lesscoding.service.EntryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/12/15 15:38
 * @apiNote
 */
@Service
public class EntryItemServiceImpl extends ServiceImpl<EntryItemMapper, EntryItem> implements EntryItemService {

    @Autowired
    private EntryItemMapper entryMapper;
    @Override
    public Integer editEntryItem(EntryItem entryItem) {
        return entryItem.getId() == null ?
                entryMapper.insert(entryItem):
                entryMapper.updateById(entryItem);
    }

    @Override
    public Page<EntryItem> getEntryItemPage(EntryItem entryItem) {
        Page<EntryItem> page = entryItem.getPage();
        QueryWrapper<EntryItem> entryItemQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(entryItem.getEntryName())) {
            entryItemQueryWrapper.like("entry_name", entryItem.getEntryName());
        }
        entryMapper.selectPage(page, entryItemQueryWrapper);
        return page;
    }
}
