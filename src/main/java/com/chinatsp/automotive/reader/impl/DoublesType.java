package com.chinatsp.automotive.reader.impl;

import com.chinatsp.automotive.reader.api.IClassType;
import com.chinatsp.automotive.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static com.chinatsp.automotive.utils.Constant.LINE;

@Slf4j
@Component
public class DoublesType implements IClassType {
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle double type");
        try {
            field.set(object, ConvertUtils.convertDoubleArrays(cellValue, LINE));
        } catch (Exception e) {
            String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
            throw new RuntimeException(error);
        }
    }
}
