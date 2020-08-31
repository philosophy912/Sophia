package com.chinatsp.code.utils;

import com.philosophy.base.util.ParseUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @date 2020-08-31 21:16
 */
@Component
public class EnumUtils {


    @SneakyThrows
    public List<String> getEnumValues(Class clazz) {
        List<String> values = new ArrayList<>();
        if (!clazz.isEnum()) {
            throw new RuntimeException("clazz is not enum");
        }
        Object[] objects = clazz.getEnumConstants();
        Method method = clazz.getMethod("getValue");
        for (Object object : objects) {
            String value = (String) method.invoke(object);
            values.add(value);
        }
        return values;
    }

}
