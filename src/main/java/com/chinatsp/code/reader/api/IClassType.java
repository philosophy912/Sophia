package com.chinatsp.code.reader.api;

import java.lang.reflect.Field;

public interface IClassType {

    void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index);
}
