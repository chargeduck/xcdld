package net.lesscoding.utils;
import java.time.LocalDateTime;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
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
            String str = String.format("【%s】: %s %s %.3f",
                    item.getEntryName(),
                    item.getEntryIntro(),
                    item.getFixed() ? "固定增加" : "百分比增加" ,
                    item.getNum());
            resultList.add(str);
        });
        return resultList;
    }

    public static void main(String[] args) {
        ThingsEntry te1 = new ThingsEntry();
        te1.setThingsId(0);
        te1.setEntryId(0);
        te1.setFixed(true);
        te1.setNum(5D);
        te1.setType(0);
        te1.setEntryName("灼烧");
        te1.setEntryIntro("附加灼烧伤害");


        ThingsEntry te2 = new ThingsEntry();
        te2.setThingsId(0);
        te2.setEntryId(0);
        te2.setFixed(false);
        te2.setNum(0.001);
        te2.setType(0);
        te2.setEntryName("闪避率");
        te2.setEntryIntro("提升闪避率");
        System.out.println(EntryUtil.getEntriesStr(Lists.newArrayList(te1, te2)));
    }
}
