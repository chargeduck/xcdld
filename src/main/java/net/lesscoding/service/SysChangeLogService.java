package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.SysChangeLog;


/**
* @author Lan
* @time 2023-11-07 17:30:49
*/
public interface SysChangeLogService extends IService<SysChangeLog> {

    /**
     * 插件端校验是否更新
     * @param log
     * @return
     */
    SysChangeLog pluginCheckUpgrade(SysChangeLog log);
}