package net.lesscoding;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.model.dto.AnnounceDto;
import net.lesscoding.utils.WebSocketUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;


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
        socketUtil.announce(
                new AnnounceDto(
                        "游戏更新公告:\n" +
                                "1.登录方式变更\n" +
                                "2.pvp新增分页跳转和查询功能\n" +
                                "3.鱼塘聊天新增 #mask 屏蔽发言功能g",
                        0));
    }


}
