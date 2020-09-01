package com.chinatsp.code.utils;

import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lizhe
 * @date 2020-09-01 21:38
 */
@Component
public class ConvertUtils {
    /**
     * 获取Pair中的类型
     *
     * @param typeName 类型名， 如java.util.List<com.philosophy.base.common.Pair<java.lang.Integer, java.lang.Integer>>
     * @return
     */
    public static Pair<String, String> getTypes(String typeName) {
        typeName = typeName.replace("java.util.List", "");
        typeName = typeName.replace("<com.philosophy.base.common.Pair<", "");
        typeName = typeName.replace(">>", "");
        String[] types = typeName.split(",");
        return new Pair<>(types[0].trim(), types[1].trim());
    }

    /**
     * 将CellValue转换成String集合
     *
     * @param value 单元格中的数据
     * @return String集合
     */
    public List<String> convertStrings(String value) {
        String[] values = value.split("\r\n");
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
     * 将CellValue转换成Integer集合
     *
     * @param value 单元格中的数据
     * @return Integer集合
     */
    public List<Integer> convertIntegers(String value) {
        List<String> values = convertStrings(value);
        List<Integer> integers = new LinkedList<>();
        values.forEach(s -> integers.add(Integer.parseInt(s)));
        return integers;
    }
}
