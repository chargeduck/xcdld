package net.lesscoding.test;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.MainApp;
import net.lesscoding.model.dto.AnnounceDto;
import net.lesscoding.utils.WebSocketUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author eleven
 * @date 2023/11/6 14:39
 * @apiNote
 */
@SpringBootTest(classes = MainApp.class)
@RunWith(SpringRunner.class)
@RefreshScope
@Slf4j
public class WebSocketTest {

    @Autowired
    private Gson gson;

    @Autowired
    private WebSocketUtil socketUtil;

    @Test
    public void webSocket() throws Exception {
        for (int i = 0; i < 3; i++) {
            socketUtil.announce(
                    new AnnounceDto(
                            "游戏更新公告:\n" +
                                    "1.背包功能完善\n"+
                                    "2.技能面板完善\n" +
                                    "3.副本锦囊新增开启功能，有几率开出武器，技能，道具等"
                            ,
                            5000));
        }

    }

    @Test
    public void strTest() {
        String input = "rakeshranjankumar";

        Map<Character, Long> charCountMap = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                ));

        charCountMap.forEach((character, count) -> {
            if (count > 1) {
                System.out.println("Character: " + character + ", Count: " + count);
            }
        });
    }


}
