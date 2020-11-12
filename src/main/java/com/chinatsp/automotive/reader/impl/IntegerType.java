package com.chinatsp.automotive.reader.impl;

import com.chinatsp.automotive.reader.api.IClassType;
import com.chinatsp.automotive.utils.ConvertUtils;
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
            // 忽略ElementAction中的SlideTimes以及BatteryAction中的RepeatTimes
            String lowerClassName = className.toLowerCase();
            boolean ignoreElementAction = lowerClassName.contains("ElementAction".toLowerCase());
            boolean ignoreBatteryAction = lowerClassName.contains("BatteryAction".toLowerCase());
            boolean ignoreRelayAction = lowerClassName.contains("RelayAction".toLowerCase());
            boolean ignoreImageCompare = lowerClassName.contains("ImageCompare".toLowerCase());
            boolean ignoreCanCompare = lowerClassName.contains("CanCompare".toLowerCase());
            if (ignoreElementAction || ignoreBatteryAction || ignoreRelayAction || ignoreImageCompare || ignoreCanCompare) {
                field.set(object, 0);
            } else {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        }
    }
}
