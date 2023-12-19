package net.lesscoding.model.chat;

import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author eleven
 * @date 2023/11/6 16:17
 * @apiNote
 */
@Data
@AllArgsConstructor
@Slf4j
public class LoginMsg {

    private String username;
    private String status;
    private Boolean reconnected;
    private String pluginVersion;
    private String token;
    private String uuid;
    private String platform;

    public LoginMsg() {
        this.username = String.format("爱坤公告发布_%s", LocalTime.now().format(DateTimeFormatter.ofPattern("mmssS")));
        this.platform = "IDEA";
        this.reconnected = false;
        this.uuid = "FF-FF-FF-FF-FB";
        this.pluginVersion = "1.6.7-beta";
        this.status = "FISHING";

    }
}
