package com.chinatsp.automotive.reader.impl;

import com.chinatsp.automotive.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.automotive.enumeration.TestCaseFunctionTypeEnum;
import com.chinatsp.automotive.reader.api.IClassType;
import com.chinatsp.automotive.utils.ConvertUtils;
import com.philosophy.base.common.Pair;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.automotive.utils.Constant.EQUAL;
import static com.chinatsp.automotive.utils.Constant.LINE;

@Slf4j
@Component
public class ListType implements IClassType {
    @SneakyThrows
    @Override
    public void setValue(Object object, Field field, Class<?> clazz, String className, String cellValue, int index) {
        /*
         * 特别处理LIST，因为有多种类型
         *  values : java.util.List : <java.lang.Double>
         *  points : java.util.List : <com.philosophy.base.common.Pair<java.lang.Integer, java.lang.Integer>>
         *  signals : java.util.List : <com.philosophy.base.common.Pair<java.lang.String, java.lang.String>>
         *  locators : java.util.List : <java.util.Map<java.lang.String, java.lang.String>>
         *  params : java.util.List : <java.lang.String>
         *  positions : java.util.List : <java.lang.Integer[]>
         *  elementAttributes : java.util.List : <com.chinatsp.code.enumeration.ElementAttributeEnum>
         */
        log.trace("handle list type");
        Type genericType = field.getGenericType();
        String typeName = genericType.getTypeName();
        log.debug("type name = {}", typeName);
        if (typeName.contains(Pair.class.getName())) {
            // 特别注意Pair有两个方式，一个是全Integer，一个是全String
            if (typeName.contains("String")) {
                if (typeName.contains("TestCaseFunctionTypeEnum")) {
                    try {
                        List<Pair<TestCaseFunctionTypeEnum, String>> pairs = ConvertUtils.convertPairTestCaseFunctionTypeString(cellValue, EQUAL);
                        field.set(object, pairs);
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "], 错误信息[" + e.getMessage() + "]";
                        throw new RuntimeException(error);
                    }
                } else {
                    try {
                        List<Pair<String, String>> pairs = ConvertUtils.convertPairStringString(cellValue, EQUAL);
                        field.set(object, pairs);
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "], 错误信息[" + e.getMessage() + "]";
                        throw new RuntimeException(error);
                    }
                }
            } else if (typeName.contains("Integer")) {
                try {
                    List<Pair<Integer, Integer>> pairs = ConvertUtils.convertPairIntegerInteger(cellValue, LINE);
                    field.set(object, pairs);
                } catch (Exception e) {
                    String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "], 错误信息[" + e.getMessage() + "]";
                    throw new RuntimeException(error);
                }
            } else {
                String error = "can not support type now";
                throw new RuntimeException(error);
            }
        } else if (typeName.contains(Map.class.getName())) {
            if (typeName.contains("String")) {
                try {
                    List<Map<AndroidLocatorTypeEnum, String>> mapList = ConvertUtils.convertMapStringString(cellValue);
                    field.set(object, mapList);
                } catch (Exception e) {
                    String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                    throw new RuntimeException(error);
                }
            } else {
                String error = "can not support type now";
                throw new RuntimeException(error);
            }
        } else {
            ParameterizedType pt = (ParameterizedType) genericType;
            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
            if (genericClazz.isEnum()) {
                // 此处的o是枚举
                Method method = genericClazz.getMethod("fromValue", String.class);
                List<String> strings = ConvertUtils.convertStrings(cellValue);
                List<Object> lists = new LinkedList<>();
                try {
                    // 遍历获取枚举
                    for (String s : strings) {
                        lists.add(method.invoke(null, s));
                    }
                } catch (Exception e) {
                    String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                    throw new RuntimeException(error);
                }
                field.set(object, lists);
            } else if (genericClazz == String.class) {
                List<String> strings;
                try {
                    strings = ConvertUtils.convertStrings(cellValue);
                } catch (Exception e) {
                    String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                    throw new RuntimeException(error);
                }
                field.set(object, strings);
            } else if (genericClazz == Integer[].class) {
                List<Integer[]> integers;
                try {
                    integers = ConvertUtils.convertIntegerArrays(cellValue, LINE);
                } catch (Exception e) {
                    String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                    throw new RuntimeException(error);
                }
                field.set(object, integers);

            }
        }
    }

}
