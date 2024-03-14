package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.SysChangeLog;
import net.lesscoding.mapper.SysChangeLogMapper;
import net.lesscoding.service.SysChangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Lan
* @time 2023-11-07 17:30:49
*/
@Service
@Slf4j
public class SysChangeLogServiceImpl extends ServiceImpl<SysChangeLogMapper, SysChangeLog> implements SysChangeLogService {

    @Autowired
    private SysChangeLogMapper changeLogMapper;

    public SysChangeLog pluginCheckUpgrade(SysChangeLog changeLog) {
        SysChangeLog sysChangeLog = changeLogMapper.selectPluginLastChangeLog();
        log.info("当前的变更记录 {}", sysChangeLog);
        return sysChangeLog;
    }
}