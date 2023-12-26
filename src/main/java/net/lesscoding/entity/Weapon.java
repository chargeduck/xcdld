package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/9/26 16:53
 * @apiNote 武器
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_weapon")
@NoArgsConstructor
public class Weapon extends BaseEntity {
    /**
     * 名称
     */
    private String name;

    /**
     * 最小攻击
     */
    private Integer minDamage;

    /**
     * 最大攻击
     */
    private Integer maxDamage;
    /**
     * 描述
     */
    private String intro;
    /**
     * 售卖价格
     */
    private Integer sellingPrice;
    /**
     * 武器类型 0小型武器 1中型武器 2大型武器 3投掷武器
     */
    private Integer type;
    /**
     * 品级 0白 1绿 2蓝 3紫 4粉 5传说 6史诗
     */
    private Integer grade;

    //private Integer star;
    //
    //private Integer level;

    public Weapon(String name) {
        this.name = name;
        this.minDamage = 3;
        this.maxDamage = 7;
        this.intro = "测试";
    }
}
