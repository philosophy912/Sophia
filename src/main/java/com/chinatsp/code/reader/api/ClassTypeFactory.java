package com.chinatsp.code.reader.api;

import com.chinatsp.code.reader.impl.BooleanType;
import com.chinatsp.code.reader.impl.DoubleType;
import com.chinatsp.code.reader.impl.DoublesType;
import com.chinatsp.code.reader.impl.EnumType;
import com.chinatsp.code.reader.impl.FloatType;
import com.chinatsp.code.reader.impl.IntegerType;
import com.chinatsp.code.reader.impl.ListType;
import com.chinatsp.code.reader.impl.LongType;
import com.chinatsp.code.reader.impl.MapType;
import com.chinatsp.code.reader.impl.StringType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class ClassTypeFactory {
    @Resource
    private BooleanType booleanType;
    @Resource
    private DoublesType doublesType;
    @Resource
    private DoubleType doubleType;
    @Resource
    private EnumType enumType;
    @Resource
    private FloatType floatType;
    @Resource
    private IntegerType integerType;
    @Resource
    private ListType listType;
    @Resource
    private LongType longType;
    @Resource
    private StringType stringType;
    @Resource
    private MapType mapType;


    public IClassType getClassType(Class<?> clazz) {
        if (clazz.isEnum()) {
            return enumType;
        } else if (clazz.equals(String.class)) {
            return stringType;
        } else if (clazz.equals(Integer.class)) {
            return integerType;
        } else if (clazz.equals(Boolean.class)) {
            return booleanType;
        } else if (clazz.equals(Float.class)) {
            return floatType;
        } else if (clazz.equals(Long.class)) {
            return longType;
        } else if (clazz.equals(Double.class)) {
            return doubleType;
        } else if (clazz.equals(Double[].class)) {
            return doublesType;
        } else if (clazz.equals(List.class)) {
            return listType;
        } else if (clazz.equals(Map.class)) {
            return mapType;
        }
        String error = "can not support class type[" + clazz.getName() + "]";
        log.debug(error);
        // throw new RuntimeException(error);
        return null;
    }

}


