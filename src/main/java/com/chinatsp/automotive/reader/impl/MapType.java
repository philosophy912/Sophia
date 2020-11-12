package com.chinatsp.automotive.reader.impl;

import com.chinatsp.automotive.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.automotive.reader.api.IClassType;
import com.chinatsp.automotive.utils.ConvertUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

@Slf4j
@Component
public class MapType implements IClassType {

    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {

        log.trace("handle map type");
        Type genericType = field.getGenericType();
        String typeName = genericType.getTypeName();
        log.trace("type name = {}", typeName);
        if (typeName.contains("AndroidLocatorTypeEnum")) {
            try {
                Map<AndroidLocatorTypeEnum, String> map = ConvertUtils.convertAndroidLocatorTypeString(cellValue);
                field.set(object, map);
            } catch (Exception e) {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "], " +
                        "错误信息[" + e.getMessage() + "]";
                throw new RuntimeException(error);
            }
        }
    }

}
