package net.lesscoding.model.vo;

import lombok.Data;
import net.lesscoding.entity.ThingsEntry;
import net.lesscoding.utils.EntryUtil;

import java.util.List;

/**
 * @author eleven
 * @date 2023/12/21 15:07
 * @apiNote
 */
@Data
public class PlayerWeaponVo {
    private String id;

    private String weaponName;

    private Integer grade;

    private Integer addLevel;

    private String intro;

    private Integer weaponType;

    private String iconUrl;

    private Integer sellingPrice;

    private Integer minDamage;

    private Integer maxDamage;

    private String gradeName;

    private String typeName;

    private List<ThingsEntry> thingsEntries;
    /**
     * 属性描述
     */
    private List<String> thingEntriesDesc;

    public List<String> getThingEntriesDesc() {
        return EntryUtil.getEntriesStr(thingsEntries);
    }
}
