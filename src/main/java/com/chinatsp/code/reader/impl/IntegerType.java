package com.chinatsp.code.reader.impl;

import com.chinatsp.code.reader.api.IClassType;
import com.chinatsp.code.utils.ConvertUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class IntegerType implements IClassType {

    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle integer type and class name = [{}]", className);
        try {
            field.set(object, ConvertUtils.convertInteger(cellValue));
        } catch (Exception e) {
            // 忽略ElementAction中的SlideTimes
            if (className.toLowerCase().contains("ElementAction".toLowerCase())) {
                field.set(object, 0);
            } else {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        }
    }
}
