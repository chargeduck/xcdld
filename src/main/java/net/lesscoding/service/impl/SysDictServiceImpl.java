package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.SysDict;
import net.lesscoding.mapper.SysDictMapper;
import net.lesscoding.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eleven
 * @date 2023/3/21 15:43
 * @description
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictMapper dictMapper;

    @Override
    public Integer editDict(SysDict dict) {
        if (null == dict.getId()) {
            Integer sort = dictMapper.selectMaxSort(dict);
            if (null == sort) {
                sort = -1;
            }
            dict.setSort(++sort);
            dictMapper.insert(dict);
        }
        return dictMapper.updateById(dict);
    }


    @Override
    public List<SysDict> getByType(SysDict dict) {
        String dictType = dict.getDictType();
        return dictMapper.selectList(new QueryWrapper<SysDict>()
                .eq("del_flag", false)
                .eq("dict_type", dictType));
    }



}
