package com.chinatsp.code.utils;

import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.COMMA;
import static com.chinatsp.code.utils.Constant.EQUAL;
import static com.chinatsp.code.utils.Constant.LINUX_NEXT_LINE;

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
    public List<Map<AndroidLocatorTypeEnum, String>> convertMapStringString(String value) {
        List<String> strings = convertStrings(value);
        List<Map<AndroidLocatorTypeEnum, String>> mapList = new LinkedList<>();
        strings.forEach(s -> {
            Map<AndroidLocatorTypeEnum, String> map = new HashMap<>();
            String[] locators = s.split(COMMA);
            for (String locator : locators) {
                String[] keyValue = locator.split(EQUAL);
                map.put(AndroidLocatorTypeEnum.fromValue(keyValue[0]), keyValue[1]);
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
    public List<Pair<String, String>> convertPairStringString(String value, String split) {
        List<String> strings = convertStrings(value);
        List<Pair<String, String>> pairs = new LinkedList<>();
        strings.forEach(s -> {
            String[] values = s.split(split);
            pairs.add(new Pair<>(values[0], values[1]));
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
    public List<Pair<Integer, Integer>> convertPairIntegerInteger(String value, String split) {
        List<String> strings = convertStrings(value);
        List<Pair<Integer, Integer>> pairs = new LinkedList<>();
        strings.forEach(s -> {
            String[] values = s.split(split);
            pairs.add(new Pair<>(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
        });
        return pairs;
    }

    /**
     * 将CellValue转换成String集合
     *
     * @param value 单元格中的数据
     * @return String集合
     */
    public List<String> convertStrings(String value) {
        log.trace("cell value is {}", value);
        String[] values = value.split(LINUX_NEXT_LINE);
        return new LinkedList<>(Arrays.asList(values));
    }

    /**
     * 将CellValue转换成Double集合
     *
     * @param value 单元格中的数据
     * @return Double集合
     */
    public List<Double> convertDoubles(String value) {
        List<String> values = convertStrings(value);
        List<Double> doubles = new LinkedList<>();
        values.forEach(s -> doubles.add(Double.parseDouble(s)));
        return doubles;
    }

    /**
     * 将CellValue转换成Integer数组集合
     *
     * @param value 单元格中的数据
     * @param split 分隔符
     * @return Integer数组集合
     */
    public List<Integer[]> convertIntegerArrays(String value, String split) {
        List<String> values = convertStrings(value);
        List<Integer[]> integerLists = new LinkedList<>();
        values.forEach(s -> {
            Integer[] integers = new Integer[4];
            String[] strings = s.split(split);
            for (int i = 0; i < strings.length; i++) {
                integers[i] = Integer.parseInt(strings[i]);
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
    public Integer convertInteger(String cellValue) {
        cellValue = cellValue.toLowerCase();
        if (cellValue.contains("x")) {
            cellValue = cellValue.split("x")[1];
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
    public Long convertLong(String cellValue) {
        cellValue = cellValue.toLowerCase();
        if (cellValue.contains("x")) {
            cellValue = cellValue.split("x")[1];
            return Long.parseLong(cellValue, 16);
        } else {
            return Long.parseLong(cellValue);
        }
    }

}
