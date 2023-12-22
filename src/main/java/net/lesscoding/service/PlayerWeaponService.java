package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.dto.query.QueryWeaponDto;
import net.lesscoding.model.vo.PlayerWeaponVo;

import java.util.List;

/**
 * @author eleven
 * @date 2023/11/21 15:46
 * @apiNote
 */
public interface PlayerWeaponService extends IService<PlayerWeapon> {
    /**
     * 获取玩家所有的武器
     *
     * @param dto 查询参数
     * @return
     */
    List<PlayerWeaponVo> getPlayerAllWeapon(QueryWeaponDto dto);

    /**
     *
     * @param dto
     * @return
     */
    Object getPlayerWeaponDetail(QueryWeaponDto dto);
}
