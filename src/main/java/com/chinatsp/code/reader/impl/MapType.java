package com.chinatsp.code.reader.impl;

import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.code.enumeration.TestCaseFunctionTypeEnum;
import com.chinatsp.code.reader.api.IClassType;
import com.chinatsp.code.utils.ConvertUtils;
import com.philosophy.base.common.Pair;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.EQUAL;
import static com.chinatsp.code.utils.Constant.LINE;

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
