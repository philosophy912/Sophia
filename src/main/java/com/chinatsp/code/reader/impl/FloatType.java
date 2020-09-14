package com.chinatsp.code.reader.impl;

import com.chinatsp.code.reader.api.IClassType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class FloatType implements IClassType {
    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle float type");
        Float value = Float.parseFloat(cellValue);
        field.set(object, value);
    }

}
