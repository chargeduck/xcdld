package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.ThingsEntry;
import net.lesscoding.mapper.ThingsEntryMapper;
import net.lesscoding.model.dto.save.ThingsEntrySaveDto;
import net.lesscoding.service.ThingsEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eleven
 * @date 2023/12/15 15:39
 * @apiNote
 */
@Service
public class ThingsEntryServiceImpl extends ServiceImpl<ThingsEntryMapper, ThingsEntry> implements ThingsEntryService {

    @Autowired
    private ThingsEntryMapper thingsEntryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Integer editThingsEntryBatch(ThingsEntrySaveDto dto) {
        int deleteRows = thingsEntryMapper.delete(new QueryWrapper<ThingsEntry>()
                .eq("things_id", dto.getThingsId())
                .eq("type", dto.getType()));
        List<ThingsEntry> list = dto.getThingsEntryList();
        list.forEach(item -> {
            item.setThingsId(dto.getThingsId());
            item.setType(dto.getType());
        });
        int insertRows = thingsEntryMapper.insertBatch(list);
        return deleteRows + insertRows;
    }
}
