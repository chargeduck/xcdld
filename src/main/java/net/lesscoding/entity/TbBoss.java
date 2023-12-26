package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import net.lesscoding.common.BaseEntity;
import lombok.Data;


/**
 * @author Lan
 * @time 2023-11-07 16:23:43
 * @Description: [BOSS表： tb_boss]
 */
@Data
public class TbBoss extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * BOSS名称
	 */
	@TableField("nickname")
	private String nickname;
	/**
	 * 参与最小等级
	 */
	@TableField("join_min_level")
	private Integer joinMinLevel;
	/**
	 * 参与最大等级
	 */
	@TableField("join_max_level")
	private Integer joinMaxLevel;
	/**
	 * 父类BOSS
	 */
	@TableField("parent_id")
	private Integer parentId;
	/**
	 * 父类BOSS出现概率
	 */
	@TableField("probability")
	private Integer probability;
	/**
	 * BOSS最小等级
	 */
	@TableField("level")
	private Integer level;

}