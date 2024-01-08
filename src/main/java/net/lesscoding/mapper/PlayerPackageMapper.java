package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.PlayerPackage;
import net.lesscoding.model.dto.query.QueryPackageEntryDto;
import net.lesscoding.model.vo.PackagePropsVo;

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
}
