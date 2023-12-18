package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.Skill;
import net.lesscoding.model.dto.save.SKillSaveDto;

/**
 * @author eleven
 * @date 2023/12/16 15:50
 * @apiNote
 */
public interface SkillService extends IService<Skill> {
    /**
     * 编辑技能
     * @param dto   保存的技能数据
     * @return Integer
     */
    Integer editSkill(SKillSaveDto dto);
}
