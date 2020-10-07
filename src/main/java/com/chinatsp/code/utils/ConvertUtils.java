package com.chinatsp.code.utils;

import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.code.enumeration.TestCaseFunctionTypeEnum;
import com.philosophy.base.common.Pair;
import com.philosophy.base.util.StringsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.COMMA;
import static com.chinatsp.code.utils.Constant.EQUAL;
import static com.chinatsp.code.utils.Constant.NEXT_LINE;

/**
 * @author lizhe
 * @date 2020-09-01 21:38
 */
@Component
@Slf4j
public class ConvertUtils {


    /**
     * 將CellValue转换成Map<String, String>集合
     * ps: id=test,classname=com.android.layout
     *
     * @param value 元格中的数据
     * @return Map<String, String>集合
     */
    public static List<Map<AndroidLocatorTypeEnum, String>> convertMapStringString(String value) {
        List<String> strings = convertStrings(value);
        List<Map<AndroidLocatorTypeEnum, String>> mapList = new LinkedList<>();
        strings.forEach(s -> {
            Map<AndroidLocatorTypeEnum, String> map = new HashMap<>();
            String[] locators = s.split(COMMA);
            for (String locator : locators) {
                String[] keyValue = locator.split(EQUAL);
                map.put(AndroidLocatorTypeEnum.fromValue(keyValue[0].trim()), keyValue[1].trim());
            }
            mapList.add(map);
        });
        return mapList;
    }

    /**
     * 將CellValue转换成Pair集合
     *
     * @param value 元格中的数据
     * @param split 分隔符
     * @return Pair集合
     */
    public static List<Pair<String, String>> convertPairStringString(String value, String split) {
        List<String> strings = convertStrings(value);
        List<Pair<String, String>> pairs = new LinkedList<>();
        strings.forEach(s -> {
            String[] values = s.split(split);
            pairs.add(new Pair<>(values[0].trim(), values[1].trim()));
        });
        return pairs;
    }

    /**
     * 將CellValue转换成Pair集合
     *
     * @param value 元格中的数据
     * @param split 分隔符
     * @return Pair集合
     */
    public static List<Pair<Integer, Integer>> convertPairIntegerInteger(String value, String split) {
        List<String> strings = convertStrings(value);
        List<Pair<Integer, Integer>> pairs = new LinkedList<>();
        strings.forEach(s -> {
            String[] values = s.split(split);
            pairs.add(new Pair<>(Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim())));
        });
        return pairs;
    }

    /**
     * 将CellValue转换成String集合
     * 自动去掉空行
     *
     * @param value 单元格中的数据
     * @return String集合
     */
    public static List<String> convertStrings(String value) {
        log.trace("cell value is {}", value);
        List<String> strings = new LinkedList<>();
        String[] values = value.split(NEXT_LINE);
        for(String s: values){
            if(!StringsUtils.isEmpty(s)){
                strings.add(s);
            }
        }
        return strings;
    }


    /**
     * 将CellValue转换成Double数组
     *
     * @param value 单元格中的数据
     * @return Double数组
     */
    public static Double[] convertDoubleArrays(String value, String split) {
        if (Strings.isEmpty(value)) {
            return null;
        }
        String[] values = value.split(split);
        Double[] doubles = new Double[values.length];
        for (int i = 0; i < values.length; i++) {
            doubles[i] = Double.parseDouble(values[i].trim());
        }
        return doubles;
    }

    /**
     * 将CellValue转换成Integer数组集合
     *
     * @param value 单元格中的数据
     * @param split 分隔符
     * @return Integer数组集合
     */
    public static List<Integer[]> convertIntegerArrays(String value, String split) {
        List<String> values = convertStrings(value);
        List<Integer[]> integerLists = new LinkedList<>();
        values.forEach(s -> {
            Integer[] integers = new Integer[4];
            String[] strings = s.split(split);
            for (int i = 0; i < strings.length; i++) {
                integers[i] = Integer.parseInt(strings[i].trim());
            }
            integerLists.add(integers);
        });
        return integerLists;
    }

    /**
     * 将CellValue转换成Integer
     * 主要对付有以0x方式表示的数据
     *
     * @param cellValue 单元格中的数据
     * @return Integer
     */
    public static Integer convertInteger(String cellValue) {
        cellValue = cellValue.toLowerCase();
        if (cellValue.contains("x")) {
            cellValue = cellValue.split("x")[1].trim();
            return Integer.parseInt(cellValue, 16);
        } else {
            return Integer.parseInt(cellValue);
        }
    }

    /**
     * 将CellValue转换成Long
     * 主要对付有以0x方式表示的数据
     *
     * @param cellValue 单元格中的数据
     * @return Long
     */
    public static Long convertLong(String cellValue) {
        cellValue = cellValue.toLowerCase();
        if (cellValue.contains("x")) {
            cellValue = cellValue.split("x")[1].trim();
            return Long.parseLong(cellValue, 16);
        } else {
            return Long.parseLong(cellValue);
        }
    }

    /**
     * 将CellValue转换成Pair对象
     *
     * @param cellValue 单元格中的数据
     * @param split     分隔符
     * @return List<Pair < TestCaseFunctionTypeEnum, String>>
     */
    public static List<Pair<TestCaseFunctionTypeEnum, String>> convertPairTestCaseFunctionTypeString(String cellValue, String split) {
        List<String> strings = convertStrings(cellValue);
        List<Pair<TestCaseFunctionTypeEnum, String>> pairs = new LinkedList<>();
        strings.forEach(s -> {
            String[] values = s.split(split);
            if (values.length == 2) {
                pairs.add(new Pair<>(TestCaseFunctionTypeEnum.fromValue(values[0].trim()), values[1].trim()));
            } else {
                pairs.add(new Pair<>(TestCaseFunctionTypeEnum.fromValue(values[0].trim()), null));
            }

        });
        return pairs;
    }

    /**
     * 将CellValue转换成Map对象
     *
     * @param cellValue 单元格中的数据
     * @return Map<AndroidLocatorTypeEnum, String> ，对应就是element表格中的locators
     */
    public static Map<AndroidLocatorTypeEnum, String> convertAndroidLocatorTypeString(String cellValue) {
        Map<AndroidLocatorTypeEnum, String> map = new HashMap<>();
        List<String> strings = convertStrings(cellValue);
        if (strings.size() != 1) {
            String error = "locator only support one line, but two line found";
            throw new RuntimeException(error);
        }
        String[] lines = strings.get(0).split(COMMA);
        for (String s : lines) {
            String[] keyValue = s.split(EQUAL);
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            map.put(AndroidLocatorTypeEnum.fromValue(key), value);
        }
        return map;
    }
}
