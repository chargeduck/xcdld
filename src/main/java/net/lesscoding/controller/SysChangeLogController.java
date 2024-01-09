package net.lesscoding.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import javax.annotation.Resource;
import cn.hutool.core.bean.BeanUtil;
import net.lesscoding.entity.SysChangeLog;
import net.lesscoding.model.dto.AnnounceDto;
import net.lesscoding.model.vo.SysChangeLogVO;
import net.lesscoding.service.impl.SysChangeLogServiceImpl;
import net.lesscoding.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import java.util.List;

/**
* 公告表模块
* @author Lan
* @time 2023-11-07 17:30:49
*/
@RestController
@RequestMapping("/changeLog")
public class SysChangeLogController{

	@Autowired
	private SysChangeLogServiceImpl sysChangeLogServiceImpl;
	@Autowired
	private WebSocketUtil webSocketUtil;

	/**
	* 公告表列表
	* @param sysChangeLogVO
	* @return
	*/
	@PostMapping("/list")
	public Result list(@RequestBody SysChangeLogVO sysChangeLogVO){
		return ResultFactory.success(sysChangeLogServiceImpl.list());
	}

	/**
	* 公告表表单
	* @param id
	* @return
	*/
	@GetMapping("/getById/{id}")
	public Result getById(@PathVariable("id") String id) {
		SysChangeLog entity = sysChangeLogServiceImpl.getById(id);
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
		sysChangeLogServiceImpl.saveOrUpdate(sysChangeLog);
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
		sysChangeLogServiceImpl.removeById(id);
		return ResultFactory.success("success");
	}
}
