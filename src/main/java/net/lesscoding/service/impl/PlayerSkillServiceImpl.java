package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.PlayerSkill;
import net.lesscoding.mapper.PlayerSKillMapper;
import net.lesscoding.model.dto.query.QuerySkillDto;
import net.lesscoding.model.vo.PlayerSKillVo;
import net.lesscoding.service.PlayerSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eleven
 * @date 2024/1/4 17:11
 * @apiNote
 */
@Service
public class PlayerSkillServiceImpl extends ServiceImpl<PlayerSKillMapper, PlayerSkill> implements PlayerSkillService {

    @Autowired
    private PlayerSKillMapper playerSKillMapper;

    @Override
    public List<PlayerSKillVo> getAllSkill(QuerySkillDto dto) {
        dto.setAccountId(StpUtil.getLoginIdAsInt());
        return playerSKillMapper.getPlayerSkill(dto);
    }

    @Override
    public String upgradeSkill(QuerySkillDto dto) {
        return "功能暂未实现";
    }
}
