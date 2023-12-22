package net.lesscoding.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import net.lesscoding.entity.ThingsEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eleven
 * @date 2023/12/21 15:22
 * @apiNote
 */
public class EntryUtil {
    public static List<String> getEntriesStr(List<ThingsEntry> list) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<String> resultList = new ArrayList<>(list.size());

        list.forEach(item -> {
            Object obj = item.getFixed() ?
                    item.getNum() :
                    item.getNum() * 100 +"%";
            String str = StrUtil.format("[{}]:{}\n {} {}",
                    item.getEntryName(),
                    item.getEntryIntro(),
                    item.getFixed() ? "固定增加" : "百分比增加" ,
                    obj);
            resultList.add(str);
        });
        return resultList;
    }

}
