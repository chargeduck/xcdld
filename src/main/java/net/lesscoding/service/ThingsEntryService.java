package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.ThingsEntry;
import net.lesscoding.model.dto.save.ThingsEntrySaveDto;

/**
 * @author eleven
 * @date 2023/12/15 15:38
 * @apiNote
 */
public interface ThingsEntryService extends IService<ThingsEntry> {
    Integer editThingsEntryBatch(ThingsEntrySaveDto dto);
}
