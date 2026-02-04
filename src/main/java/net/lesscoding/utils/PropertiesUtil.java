package net.lesscoding.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author eleven
 * @date 2023/1/5 14:03
 * @description
 */
@Component
@Slf4j
public class PropertiesUtil {

    @Autowired
    private Environment environment;

    @Autowired
    private Gson gson;


    @Value("${spring.profiles.active}")
    private String active;

    /**
     * dev环境启动时打印当前的所有配置文件
     * @throws Exception
     */
    @PostConstruct
    public void afterRunning() throws Exception {
        // 生产环境不输出配置信息
        //if (active.toLowerCase().contains("prod")) {
        //    return;
        //}
        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        Map<String, String> propertiesMap = new HashMap<>(propertySources.size());
        for (Iterator<PropertySource<?>> iterator = propertySources.iterator(); iterator.hasNext(); ) {
            PropertySource<?> propertySource = iterator.next();
            propertiesHandler(propertiesMap, propertySource);
        }
        log.info("======打印当前系统配置文件======");
        log.info("{}", gson.toJson(propertiesMap));
        log.info("======当前系统配置文件结束======");
        Properties properties = new Properties();
        propertiesMap.forEach((k, v) -> properties.setProperty(k, v));
        String filePath = System.getProperty("user.dir") + File.separator + "env.properties";
        try(OutputStream outputStream = new FileOutputStream(filePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)){
            properties.store(outputStreamWriter,"测试输出当前配置文件");
        }
        log.info("当前配置文件输出完成");
    }

    /**
     * 递归处理当前配置文件，将配置文件中的配置项放置到map中
     * @param propertiesMap         配置map
     * @param propertySource        配置项源
     */
    public void propertiesHandler(Map<String, String> propertiesMap, PropertySource<?> propertySource) {
        Object source = propertySource.getSource();
        if (source instanceof Map) {
            putSource(propertiesMap, source);
        } else {
            List<PropertySource<?>> sourceList = getPropertySources(propertySource, propertiesMap);
            if (CollUtil.isNotEmpty(sourceList)) {
                for (PropertySource<?> itemSource : sourceList) {
                    propertiesHandler(propertiesMap, itemSource);
                }
            }
        }
    }

    /**
     * 将配置项放置到map中
     * @param propertiesMap     配置map
     * @param source            配置源中的配置项
     */
    private void putSource(Map<String, String> propertiesMap, Object source) {
        if (source instanceof Map) {
            Map sourceMap = (Map) source;
            sourceMap.forEach((k, v) -> propertiesMap.put(String.valueOf(k), String.valueOf(v)));
        }
    }

    /**
     * 转换PropertySource获取propertySources属性值
     * 如果不存在propertySources但是有sources的话就直接吧sources放置到propertyMap中
     * @param propertySource        配置项
     * @param propertiesMap         配置Map
     * @return  List                配置项集合
     */
    private List<PropertySource<?>> getPropertySources(PropertySource propertySource, Map propertiesMap) {
        Class<? extends PropertySource> propertySourceClass = propertySource.getClass();
        String simpleName = propertySourceClass.getSimpleName();
        List<PropertySource<?>> resultList = new ArrayList<>();
        switch (simpleName) {
            case "CompositePropertySource":
                CompositePropertySource compositePropertySource = (CompositePropertySource) propertySource;
                resultList.addAll(compositePropertySource.getPropertySources());
                return resultList;
            case "OriginTrackedMapPropertySource":
                OriginTrackedMapPropertySource originTrackedMapPropertySource = (OriginTrackedMapPropertySource) propertySource;
                putSource(propertiesMap, originTrackedMapPropertySource.getSource());
                return resultList;
            case "MapPropertySource":
                MapPropertySource mapPropertySource = (MapPropertySource) propertySource;
                putSource(propertiesMap, mapPropertySource.getSource());
                return resultList;
            case "PropertiesPropertySource":
                PropertiesPropertySource propertiesPropertySource = (PropertiesPropertySource) propertySource;
                putSource(propertiesMap, propertiesPropertySource.getSource());
                return resultList;
            default:
                return resultList;
        }
    }

}
