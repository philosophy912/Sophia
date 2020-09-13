package com.chinatsp.code.reader.impl;

import com.chinatsp.code.reader.api.BaseType;
import com.chinatsp.code.reader.api.IClassType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static com.chinatsp.code.utils.Constant.CHINESE_YES;
import static com.chinatsp.code.utils.Constant.YES;

@Slf4j
@Component
public class BooleanType extends BaseType implements IClassType {
    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle boolean type");
        boolean flag = cellValue.equalsIgnoreCase(YES) || cellValue.equalsIgnoreCase(CHINESE_YES);
        field.set(object, flag);
    }

}
