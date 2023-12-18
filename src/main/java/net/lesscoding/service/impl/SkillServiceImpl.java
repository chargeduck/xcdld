package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.Skill;
import net.lesscoding.mapper.SKillMapper;
import net.lesscoding.model.dto.save.SKillSaveDto;
import net.lesscoding.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/12/16 15:50
 * @apiNote
 */
@Service
public class SkillServiceImpl extends ServiceImpl<SKillMapper, Skill> implements SkillService {
    @Autowired
    private SKillMapper sKillMapper;

    @Override
    public Integer editSkill(SKillSaveDto dto) {
        return null;
    }
}
