package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.DropDetail;
import net.lesscoding.entity.GameProps;
import net.lesscoding.entity.Skill;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.DropDetailMapper;
import net.lesscoding.mapper.GamePropsMapper;
import net.lesscoding.mapper.SKillMapper;
import net.lesscoding.mapper.WeaponMapper;
import net.lesscoding.service.GamePropsService;
import net.lesscoding.utils.OpenPackageUtil;
import net.sf.jsqlparser.expression.CollateExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/10/31 9:25
 * @apiNote
 */
@Service
@Slf4j
public class GamePropsServiceImpl extends ServiceImpl<GamePropsMapper, GameProps> implements GamePropsService {

    @Autowired
    private  GamePropsMapper propsMapper;
    @Autowired
    private DropDetailMapper dropDetailMapper;
    @Autowired
    private GamePropsMapper gamePropsMapper;
    @Autowired
    private WeaponMapper weaponMapper;
    @Autowired
    private SKillMapper sKillMapper;

    @Override
    public Integer editGameProps(GameProps props) {
        Integer type = props.getType();
        if (type == null) {
            throw new RuntimeException("道具类型不能为空");
        }
        //0 锦囊 1礼包 2材料 3技能卡 4宝石 5消耗品
        switch (type) {
            case 0:
            case 1:
            case 3:
                props.setCanUse(true);
                props.setAllowOpen(true);
                break;
            case 5:
                props.setCanUse(true);
                props.setAllowOpen(false);
                break;
            default:
                props.setCanUse(false);
                props.setAllowOpen(false);
        }
        return null;
    }

    @Override
    public Integer updatePropsIntro(GameProps props) {
        Integer id = props.getId();
        if (id == null ) {
            throw new RuntimeException("道具id不允许为空");
        }
        List<DropDetail> dropDetailList = dropDetailMapper.selectList(new QueryWrapper<DropDetail>()
                .eq("del_flag", false)
                .eq("props_id", id));
        List<String> introStrList = new ArrayList<>(dropDetailList.size());
        String formatStr = "{}% 概率获得{}【{}】*{}";
        if (CollUtil.isNotEmpty(dropDetailList)) {
            // 道具
            List<DropDetail> dropProps = OpenPackageUtil.getDropDetailList(dropDetailList, 0);
            if (CollUtil.isNotEmpty(dropProps)) {
                Map<Integer, String> propsMap = gamePropsMapper.selectList(new QueryWrapper<GameProps>().in("id", OpenPackageUtil.getDropObjIds(dropProps)))
                        .stream()
                        .collect(Collectors.toMap(GameProps::getId, GameProps::getPropsName));
                dropProps.forEach(item -> introStrList.add(
                        StrUtil.format(formatStr,
                                getDropProbabilityStr(item.getDropProbability()),
                                "道具",
                                propsMap.get(item.getObjId()),
                                item.getNum()
                                )
                ));
            }

            List<DropDetail> dropWeapons = OpenPackageUtil.getDropDetailList(dropDetailList, 1);
            if (CollUtil.isNotEmpty(dropWeapons)) {
                Map<Integer, String> weaponMap = weaponMapper.selectList(new QueryWrapper<Weapon>().in("id", OpenPackageUtil.getDropObjIds(dropWeapons)))
                        .stream()
                        .collect(Collectors.toMap(Weapon::getId, Weapon::getName));
                dropWeapons.forEach(item -> introStrList.add(
                        StrUtil.format(formatStr,
                                getDropProbabilityStr(item.getDropProbability()),
                                "武器",
                                weaponMap.get(item.getObjId()),
                                item.getNum()
                        )
                ));
            }

            List<DropDetail> dropSKills = OpenPackageUtil.getDropDetailList(dropDetailList, 2);
            if (CollUtil.isNotEmpty(dropSKills)) {
                Map<Integer, String> skillMap = sKillMapper.selectList(new QueryWrapper<Skill>().in("id", OpenPackageUtil.getDropObjIds(dropSKills)))
                        .stream()
                        .collect(Collectors.toMap(Skill::getId, Skill::getSkillName));
                dropSKills.forEach(item -> introStrList.add(
                        StrUtil.format(formatStr,
                                getDropProbabilityStr(item.getDropProbability()),
                                "技能",
                                skillMap.get(item.getObjId()),
                                item.getNum()
                        )
                ));
            }


            List<DropDetail> dropGolds = OpenPackageUtil.getDropDetailList(dropDetailList, 3);
            if (CollUtil.isNotEmpty(dropGolds)) {
                dropGolds.forEach(item -> introStrList.add(
                        StrUtil.format(formatStr,
                                getDropProbabilityStr(item.getDropProbability()),
                                "道具",
                                "金币",
                                item.getNum())
                ));
            }
        }
        introStrList.sort(Comparator.reverseOrder());
        props = propsMapper.selectById(id);
        props.setUpdateBy(StpUtil.getLoginIdAsInt());
        props.setUpdateTime(LocalDateTime.now());
        props.setPropsIntro(StrUtil.join("\n", introStrList));
        return propsMapper.updateById(props);
    }

    private String getDropProbabilityStr(double number) {
        return String.format("%.2f", number * 100);
    }
}
