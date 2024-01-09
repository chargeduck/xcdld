package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.PlayerSkill;
import net.lesscoding.model.dto.query.QuerySkillDto;
import net.lesscoding.model.vo.PlayerSKillVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eleven
 * @date 2024/1/4 17:06
 * @apiNote
 */
public interface PlayerSKillMapper extends BaseMapper<PlayerSkill> {
    List<PlayerSKillVo> getPlayerSkill(QuerySkillDto dto);

    /**
     * 批量插入数据
     * @param insertProps   插入数据
     * @return Integer      数据库受影响的行数
     */
    Integer insertBatch(@Param("list") List<PlayerSkill> insertProps);
}
