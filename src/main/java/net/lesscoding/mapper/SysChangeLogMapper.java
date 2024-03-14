package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.SysChangeLog;


/**
* @author Lan
* @time 2023-11-07 17:30:49
*/
public interface SysChangeLogMapper extends BaseMapper<SysChangeLog>{

    SysChangeLog selectPluginLastChangeLog();
}
