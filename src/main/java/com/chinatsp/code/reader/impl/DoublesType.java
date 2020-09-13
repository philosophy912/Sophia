package com.chinatsp.code.reader.impl;

import com.chinatsp.code.reader.api.BaseType;
import com.chinatsp.code.reader.api.IClassType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static com.chinatsp.code.utils.Constant.LINE;

@Slf4j
@Component
public class DoublesType extends BaseType implements IClassType {
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle double type");
        try {
            field.set(object, convertUtils.convertDoubleArrays(cellValue, LINE));
        } catch (Exception e) {
            String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
            throw new RuntimeException(error);
        }
    }
}
