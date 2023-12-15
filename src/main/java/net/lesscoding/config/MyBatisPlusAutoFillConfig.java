package net.lesscoding.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author eleven
 * @date 2022/11/11 12:23
 * @description MybatisPlus自动填充配置类
 */
@Configuration
public class MyBatisPlusAutoFillConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        Integer userId = getCurrentUserId();
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
        this.setFieldValByName("delFlag", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
            Integer userId = getCurrentUserId();
            this.setFieldValByName("updateBy", userId, metaObject);
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }



    /**
     * 获取是否有创建人
     *
     * @param metaObject 自动填充对象
     * @return Boolean
     */
    private Boolean notHasCreateBy(MetaObject metaObject) {
        Object meta = metaObject.getOriginalObject();
        Class<?> cls = meta.getClass();
        try {
            PropertyDescriptor createByField = new PropertyDescriptor("createBy", cls);
            Method readMethod = createByField.getReadMethod();
            Object invoke = readMethod.invoke(meta);
            return null == invoke;
        } catch (Exception e) {
            return true;
        }
    }

    public Integer getCurrentUserId() {
        return StpUtil.getLoginId(1);
    }
}
