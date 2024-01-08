package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eleven
 * @date 2023/3/21 15:42
 * @description
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
    /**
     * 根据编码查询最大的排序
     *
     * @param dict 字典项
     * @return
     */
    Integer selectMaxSort(SysDict dict);

    List<SysDict> selectByType(@Param("dictType") String dictType);
    SysDict selectByTypeAndCode(@Param("dictType") String dictType, @Param("dictCode") String dictCode);

}