package net.lesscoding.model.dto;

import lombok.Data;

/**
 * @author eleven
 * @date 2024/1/9 8:34
 * @apiNote
 */
@Data
public class PropsOperationDto {
    /**
     * 背包的id
     */
    private Integer id;

    private Integer playerId;

    private Integer accountId;
    /**
     * 使用数量
     */
    private Long number;
}
