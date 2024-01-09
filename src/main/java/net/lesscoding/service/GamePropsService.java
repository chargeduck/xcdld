package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.GameProps;

/**
 * @author eleven
 * @date 2023/10/31 9:23
 * @apiNote
 */
public interface GamePropsService extends IService<GameProps> {

    /**
     * 编辑道具
     * @param props     道具
     * @return Integer  保存的记录数
     */
    Integer editGameProps(GameProps props);

    /**
     * 更新描述
     * @param props     道具
     * @return Integer  受影响的行数
     */
    Integer updatePropsIntro(GameProps props);
}
