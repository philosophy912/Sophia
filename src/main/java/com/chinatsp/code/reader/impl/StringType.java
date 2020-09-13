package com.chinatsp.code.reader.impl;

import com.chinatsp.code.reader.api.BaseType;
import com.chinatsp.code.reader.api.IClassType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class StringType extends BaseType implements IClassType {
    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle string type");
        field.set(object, cellValue);
    }

}
