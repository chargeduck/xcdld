package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.GameProps;
import net.lesscoding.mapper.GamePropsMapper;
import net.lesscoding.service.GamePropsService;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/31 9:25
 * @apiNote
 */
@Service
public class GamePropsServiceImpl extends ServiceImpl<GamePropsMapper, GameProps> implements GamePropsService {
}
