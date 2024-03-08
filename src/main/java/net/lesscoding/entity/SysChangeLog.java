package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.lesscoding.common.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author Lan
 * @time 2023-11-07 17:30:49
 * @Description: [公告表： sys_change_log]
 */
@Data
public class SysChangeLog extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 公告内容
	 */
	@TableField("content")
	private String content;

	private String url;

	@TableField("sha512")
	private String sha512;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/ShangHai")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime publishTime;

	/**
	 * 版本号
	 */
	private String version;

	@TableField(exist = false)
	private String 	publishTimeStr;

	public String getPublishTimeStr() {
		if (publishTime == null) {
			publishTime = LocalDateTime.parse("2024-01-01 00:00:00");
		}
		return publishTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}