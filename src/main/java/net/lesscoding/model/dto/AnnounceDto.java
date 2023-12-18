package net.lesscoding.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eleven
 * @date 2023/11/7 16:39
 * @apiNote 公告dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnounceDto {

    private String content;

    private Integer delayMilliseconds;

    public Integer getDelayMilliseconds() {
        return delayMilliseconds == null ? 0 : delayMilliseconds;
    }
}
