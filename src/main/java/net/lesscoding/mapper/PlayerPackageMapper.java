package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.PlayerPackage;
import net.lesscoding.model.dto.PropsOperationDto;
import net.lesscoding.model.dto.query.QueryPackageEntryDto;
import net.lesscoding.model.vo.PackagePropsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/31 9:46
 * @apiNote
 */
public interface PlayerPackageMapper extends BaseMapper<PlayerPackage> {

    /**
     * 查询数据
     * @param dto   查询参数
     * @return List
     */
    List<PackagePropsVo> selectPackageProps(QueryPackageEntryDto dto);

    Integer discardProps(PropsOperationDto dto);

    /**
     * 批量插入数据
     * @param insertProps   批量插入的数据
     * @return Integer      数据库受影响的行数
     */
    Integer insertBatch(@Param("list") List<PlayerPackage> insertProps);

    /**
     * 减少道具
     * @param dto   减少数据
     * @return
     */
    Integer substractProps(PropsOperationDto dto);
}
