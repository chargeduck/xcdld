package net.lesscoding.model.dto.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author eleven
 * @date 2024/1/4 16:45
 * @apiNote
 */
@Data
public class QuerySkillDto {
    private Integer id;

    private Integer playerId;

    private Integer accountId;

    private Integer skillId;

    private String skillLevel;

    private Integer grade;

    private Page page;

}
