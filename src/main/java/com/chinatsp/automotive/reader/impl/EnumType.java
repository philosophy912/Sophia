package com.chinatsp.automotive.reader.impl;

import com.chinatsp.automotive.reader.api.IClassType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
@Component
public class EnumType implements IClassType {

    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.debug("handle enum type");
        // 此处的o是枚举
        Method method = clazz.getMethod("fromValue", String.class);
        try {
            field.set(object, method.invoke(null, cellValue));
        } catch (Exception e) {
            String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
            throw new RuntimeException(error);
        }
    }
}
