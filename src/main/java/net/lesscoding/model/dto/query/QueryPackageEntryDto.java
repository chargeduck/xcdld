package net.lesscoding.model.dto.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * @author eleven
 * @date 2024/1/4 16:45
 * @apiNote
 */
@Data
public class QueryPackageEntryDto {
    private Integer id;

    private Integer playerId;

    private Integer accountId;

    private Integer entryId;

    private String type;

    private List<String> propsType;

    private Page page;

}
