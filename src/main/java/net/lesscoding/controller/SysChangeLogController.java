package net.lesscoding.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.SysChangeLog;
import net.lesscoding.model.dto.AnnounceDto;
import net.lesscoding.model.vo.SysChangeLogVO;
import net.lesscoding.service.SysChangeLogService;
import net.lesscoding.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* 公告表模块
* @author Lan
* @time 2023-11-07 17:30:49
*/
@RestController
@RequestMapping("/changeLog")
public class SysChangeLogController{

	@Autowired
	private SysChangeLogService changeLogService;
	@Autowired
	private WebSocketUtil webSocketUtil;

	/**
	* 公告表列表
	* @param sysChangeLogVO
	* @return
	*/
	@PostMapping("/list")
	public Result list(@RequestBody SysChangeLogVO sysChangeLogVO){
		return ResultFactory.success(changeLogService.list());
	}

	/**
	* 公告表表单
	* @param id
	* @return
	*/
	@GetMapping("/getById/{id}")
	public Result getById(@PathVariable("id") String id) {
		SysChangeLog entity = changeLogService.getById(id);
		return ResultFactory.success(entity);
	}

	/**
	* 公告表保存或修改
	* @param sysChangeLogVO
	* @return
	*/
	@PostMapping("/edit")
	public Result saveOrUpdate(@RequestBody SysChangeLogVO sysChangeLogVO) {
		SysChangeLog sysChangeLog = BeanUtil.copyProperties(sysChangeLogVO, SysChangeLog.class);
		changeLogService.saveOrUpdate(sysChangeLog);
		try {
			webSocketUtil.announce(new AnnounceDto(sysChangeLog.getContent(), 500));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return ResultFactory.success("success");
	}

	/**
	* 公告表删除
	* @param id
	* @return
	*/
	@PostMapping("/delete/{id}")
	public Result delete(@PathVariable("id") String id) {
		changeLogService.removeById(id);
		return ResultFactory.success("success");
	}

	@SaIgnore
	@PostMapping("/pluginCheckUpgrade")
	public Result pluginCheckUpgrade(@RequestBody SysChangeLog log) {
		return ResultFactory.success(changeLogService.pluginCheckUpgrade(log));
	}
}
