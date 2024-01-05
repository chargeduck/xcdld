package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.PlayerSkill;
import net.lesscoding.model.dto.query.QuerySkillDto;
import net.lesscoding.model.vo.PlayerSKillVo;

import java.util.List;

/**
 * @author eleven
 * @date 2024/1/4 17:10
 * @apiNote
 */
public interface PlayerSkillService extends IService<PlayerSkill> {
    /**
     * 获取自己的所有技能
     * @param dto
     * @return
     */
    List<PlayerSKillVo> getAllSkill(QuerySkillDto dto);

    /**
     * 升级技能
     * @param dto
     * @return
     */
    String upgradeSkill(QuerySkillDto dto);
}
