package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.ThingsEntry;
import net.lesscoding.model.dto.save.ThingsEntrySaveDto;
import net.lesscoding.service.ThingsEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author eleven
 * @date 2023/12/15 15:35
 * @apiNote
 */
@RestController
@RequestMapping("/thingsEntry")
public class ThingsEntryController {
    @Autowired
    private ThingsEntryService thingsEntryService;

    @PostMapping("/editBatch")
    public Result editThingsEntryBatch(@Valid @RequestBody ThingsEntrySaveDto dto) {
        return ResultFactory.success(thingsEntryService.editThingsEntryBatch(dto));
    }

}
