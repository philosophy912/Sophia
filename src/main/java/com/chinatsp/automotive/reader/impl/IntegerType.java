package com.chinatsp.automotive.reader.impl;

import com.chinatsp.automotive.entity.actions.BatteryAction;
import com.chinatsp.automotive.entity.actions.ElementAction;
import com.chinatsp.automotive.entity.actions.RelayAction;
import com.chinatsp.automotive.entity.compare.CanCompare;
import com.chinatsp.automotive.entity.compare.ImageCompare;
import com.chinatsp.automotive.reader.api.IClassType;
import com.chinatsp.automotive.utils.ConvertUtils;
import com.chinatsp.automotive.utils.ReaderUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Component
public class IntegerType implements IClassType {
    @Resource
    private ReaderUtils readerUtils;

    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        log.trace("handle integer type and class name = [{}]", className);
        try {
            field.set(object, ConvertUtils.convertInteger(cellValue));
        } catch (Exception e) {
            // 忽略ElementAction中的SlideTimes以及BatteryAction中的RepeatTimes
            String lowerClassName = className.toLowerCase();
            boolean result = readerUtils.ignore(lowerClassName, ElementAction.class, BatteryAction.class, RelayAction.class, ImageCompare.class, CanCompare.class);
            if (result) {
                field.set(object, 0);
            } else {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        }
    }
}
