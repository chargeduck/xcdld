package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import net.lesscoding.common.BaseEntity;
import lombok.Data;


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

	
}