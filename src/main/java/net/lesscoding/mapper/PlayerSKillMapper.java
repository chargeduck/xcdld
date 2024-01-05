package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.PlayerSkill;
import net.lesscoding.model.dto.query.QuerySkillDto;
import net.lesscoding.model.vo.PlayerSKillVo;

import java.util.List;

/**
 * @author eleven
 * @date 2024/1/4 17:06
 * @apiNote
 */
public interface PlayerSKillMapper extends BaseMapper<PlayerSkill> {
    List<PlayerSKillVo> getPlayerSkill(QuerySkillDto dto);
}
