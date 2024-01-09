package net.lesscoding.utils;

import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import net.lesscoding.entity.DropDetail;
import net.lesscoding.entity.PlayerPackage;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class OpenPackageUtil {

    //开的箱子的id 映射 能开出的物品id
    private static Map<Integer, List<Integer>> itemMapping = new HashMap<>();

    static {
        //默认构造，可以改造成读表
        itemMapping.put(1, new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15)));
        itemMapping.put(2, new ArrayList<>(Arrays.asList(21, 22, 23, 24, 25)));
        itemMapping.put(3, new ArrayList<>(Arrays.asList(31, 32, 33, 34, 35)));
    }

    /**
     * 随机开箱
     *
     * @param playerId 用户id
     * @param objId    箱子物品id
     * @return 物品
     */
    public static PlayerPackage openRandomPackage(Integer playerId, Integer objId) {
        if (playerId == null) {
            return null;
        }
        if (objId == null) {
            return null;
        }
        List<Integer> itemList = itemMapping.get(objId);
        if (itemList == null || itemList.size() == 0) {
            return null;
        }
        //随机抽取，可以加权抽取，获取多次随机值计算后设置概率
        int seed = ThreadLocalRandom.current().nextInt(0, itemList.size());
        Integer itemId = itemList.get(seed);

        //构造背包物品对象
        PlayerPackage playerPackage = new PlayerPackage();
        playerPackage.setPlayerId(playerId);
        playerPackage.setObjId(itemId);
        //默认只能开出来一个
        playerPackage.setNum(1);

        return playerPackage;
    }
    /**
     *
     * @param dropDetailList        掉落详情
     * @param type                  0 道具 1技能 2武器 3金币
     * @return List<Integer>        掉落的东西
     */
    public static List<DropDetail> getDropDetailList(List<DropDetail> dropDetailList, Integer type) {
        return dropDetailList.stream()
                .filter(item -> item.getType().equals(type))
                .collect(Collectors.toList());
    }

    public static List<Integer> getDropObjIds(List<DropDetail> list) {
        return list.stream()
                .map(DropDetail::getObjId)
                .collect(Collectors.toList());
    }

    public static DropDetail getDropDetail(List<DropDetail> dropDetailList) {
        ArrayList<WeightRandom.WeightObj<DropDetail>> list = new ArrayList<>();
        dropDetailList.forEach(item -> list.add(new WeightRandom.WeightObj<>(item, item.getDropProbability())));
        return RandomUtil.weightRandom(list).next();
    }
}
