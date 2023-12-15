package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.EntryItem;
import net.lesscoding.service.EntryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author eleven
 * @date 2023/12/15 15:35
 * @apiNote
 */
@RestController
@RequestMapping("/entryItem")
public class EntryItemController {
    @Autowired
    private EntryItemService entryItemService;

    @PostMapping("/edit")
    public Result editEntryItem(@Valid @RequestBody EntryItem entryItem) {
        return ResultFactory.success(entryItemService.editEntryItem(entryItem));
    }

    @PostMapping("/page")
    public Result getEntryItemPage(@RequestBody EntryItem entryItem) {
        return ResultFactory.success(entryItemService.getEntryItemPage(entryItem));
    }
}
