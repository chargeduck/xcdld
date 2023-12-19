package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.GameProps;
import net.lesscoding.mapper.GamePropsMapper;
import net.lesscoding.service.GamePropsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/31 9:25
 * @apiNote
 */
@Service
public class GamePropsServiceImpl extends ServiceImpl<GamePropsMapper, GameProps> implements GamePropsService {

    @Autowired
    private  GamePropsMapper propsMapper;

    @Override
    public Integer editGameProps(GameProps props) {
        Integer type = props.getType();
        if (type == null) {
            throw new RuntimeException("道具类型不能为空");
        }
        //0 锦囊 1礼包 2材料 3技能卡 4宝石 5消耗品
        switch (type) {
            case 0:
            case 1:
            case 3:
                props.setCanUse(true);
                props.setAllowOpen(true);
                break;
            case 5:
                props.setCanUse(true);
                props.setAllowOpen(false);
                break;
            default:
                props.setCanUse(false);
                props.setAllowOpen(false);
        }
        return null;
    }
}
