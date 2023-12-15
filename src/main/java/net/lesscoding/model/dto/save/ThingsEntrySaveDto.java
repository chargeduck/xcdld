package net.lesscoding.model.dto.save;

import lombok.Data;
import net.lesscoding.entity.ThingsEntry;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author eleven
 * @date 2023/12/15 16:58
 * @apiNote
 */
@Data
public class ThingsEntrySaveDto {

    @NotNull(message = "物品id不能为空")
    private Integer thingsId;

    @NotNull(message = "类型不能为空")
    private Integer type;

    @NotNull(message = "物品词条不能为空")
    private List<ThingsEntry> thingsEntryList;
}
