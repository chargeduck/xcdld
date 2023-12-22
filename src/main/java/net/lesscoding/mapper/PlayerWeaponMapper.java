package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.dto.query.QueryWeaponDto;
import net.lesscoding.model.vo.PlayerWeaponVo;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/23 9:06
 * @apiNote
 */
public interface PlayerWeaponMapper extends BaseMapper<PlayerWeapon> {
    /**
     * 批量插入
     * @param insertList 要插入的数据
     * @return Integer 数据库受影响的行数
     */
    Integer insertBatch(List<PlayerWeapon> insertList);

    /**
     * 获取玩家所有的武器
     * @param dto
     * @return
     */
    List<PlayerWeaponVo> getPlayerAllWeapon(QueryWeaponDto dto);
}
