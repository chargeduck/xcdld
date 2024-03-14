package net.lesscoding.test;

import com.google.common.collect.Lists;
import lombok.var;
import net.lesscoding.utils.BattleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author eleven
 * @date 2023/11/9 11:00
 * @apiNote
 */
public class RandomTest {
    public static void main(String[] args) {
        ArrayList<String> allList = Lists.newArrayList("罗兰", "吉特利", "伊撒尔", "理查德", "爱德华", "莱奥", "西恩", "贝奥武夫");
        List<List<String>> shuffle = shuffle(allList);
        int i = 1;
        boolean flag = false;
        while (true) {
            List<String> winnerList = new ArrayList<>();
            System.out.println(String.format("===============【Round %d】===============", i++));
            for (List<String> group : shuffle) {
                String first = group.get(0);
                String last = group.get(1);
                System.out.println(String.format("当前组合 %s vs %s", first, last));
                Boolean weightResult = BattleUtil.getWeightResult(0.5);
                String winner = weightResult ? first : last;
                System.out.println(String.format("当前获胜者 %s", winner));
                winnerList.add(winner);
            }
            if (winnerList.size() == 1) {
                System.out.println(String.format("最终胜利者为 %s", winnerList.get(0)));
                break;
            } else {
                shuffle = shuffle(winnerList);
            }
        }
    }

    private static List<List<String>> shuffle(List<String> list) {
        // 随机洗牌
        Collections.shuffle(list);
        List<List<String>> npcGroup = new ArrayList<>();
        List<String> group = null;
        for (int i = 0; i < list.size(); i += 2) {
            group = new ArrayList<>();
            group.add(list.get(i));
            group.add(list.get(i + 1));
            npcGroup.add(group);
        }
        return npcGroup;
    }
}
