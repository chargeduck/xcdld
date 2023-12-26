package net.lesscoding.model.dto.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author eleven
 * @date 2023/11/21 15:48
 * @apiNote
 */
@Data
public class QueryWeaponDto {

    private Integer id;

    private Integer playerId;

    private Integer accountId;

    private Integer weaponId;

    private String weaponName;

    private Integer grade;
    /**
     * 丢弃类型 0丢弃 1售卖 2强化失败 3增幅失败
     */
    private Integer discardType;

    private Page page;

    public Integer getGrade() {
        return grade == null ? 0 : grade;
    }
}
