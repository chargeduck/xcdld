package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.DiscardLog;
import net.lesscoding.mapper.DiscardLogMapper;
import net.lesscoding.service.DiscardLogService;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/12/26 14:36
 * @apiNote
 */
@Service
public class DiscardLogServiceImpl extends ServiceImpl<DiscardLogMapper, DiscardLog> implements DiscardLogService {
}
