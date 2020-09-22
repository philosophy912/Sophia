package com.chinatsp.code.reader.impl;

import com.chinatsp.code.reader.api.IClassType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class DoubleType implements IClassType {
    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle double type");
        try {
            field.set(object, Double.parseDouble(cellValue));
        } catch (Exception e) {
            String lowerClassName = className.toLowerCase();
            boolean ignoreScreenOpsAction = lowerClassName.contains("ScreenOpsAction".toLowerCase());
            if (ignoreScreenOpsAction) {
                field.set(object, 0d);
            } else {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        }
    }

}
