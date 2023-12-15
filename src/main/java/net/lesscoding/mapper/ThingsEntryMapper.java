package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.ThingsEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eleven
 * @date 2023/12/15 15:36
 * @apiNote
 */
public interface ThingsEntryMapper extends BaseMapper<ThingsEntry> {
    int insertBatch(@Param("list") List<ThingsEntry> list);
}
