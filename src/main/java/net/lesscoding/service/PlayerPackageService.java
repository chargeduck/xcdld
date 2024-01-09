package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.PlayerPackage;
import net.lesscoding.model.dto.PropsOperationDto;
import net.lesscoding.model.dto.query.QueryPackageEntryDto;
import net.lesscoding.model.vo.OperaPropsVo;
import net.lesscoding.model.vo.PackagePropsVo;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/31 9:46
 * @apiNote
 */
public interface PlayerPackageService extends IService<PlayerPackage> {

    /**
     * 根据类型查看背包物品
     * @param type
     * @return
     */
    List<PlayerPackage> findByType(Integer playerId,Integer type);

    /**
     * 新增背包物品
     * @param playerPackages
     * @return
     */
    Boolean addPlayerPackage(Integer playerId,List<PlayerPackage> playerPackages);

    List<PackagePropsVo> getAllProps(QueryPackageEntryDto dto);

    /**
     * 丢弃道具
     * @param dto
     * @return
     */
    OperaPropsVo discardProps(PropsOperationDto dto);

    /**
     * 售卖道具
     * @param dto
     * @return
     */
    OperaPropsVo sellProps(PropsOperationDto dto);

    /**
     * 打开道具
     *
     * @param dto
     * @return
     */
    OperaPropsVo openProps(PropsOperationDto dto);
}
