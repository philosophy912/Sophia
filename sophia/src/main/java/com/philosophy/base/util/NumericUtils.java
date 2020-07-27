package com.philosophy.base.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lizhe
 * @version 1.0.0
 * @date 2019/10/10:11:16
 */
@Slf4j
public class NumericUtils {

    /**
     * 根据dotNum长度补0
     *
     * @param dotNum 长度
     * @return 补0后的字符串
     */
    private static String setZero(int dotNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("0.");
        for (int i = 0; i < dotNum; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

    /**
     * 根据dotNum长度生成%的字符串
     * <p>int dotNum = 2</p>
     * <p>String str = getPercentString(dotNum)</p>
     * <p>str = "0.00%</p>
     *
     * @param dotNum 长度
     * @return 生成的百分比字符
     */
    private static String getPercentString(int dotNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("0.");
        for (int i = 0; i < dotNum; i++) {
            sb.append("0");
        }
        sb.append("%");
        return sb.toString();
    }

    /**
     * 生成静态的Pattern
     *
     * @param reg   正则表达式
     * @param value 要匹配的字符串
     * @return 是否匹配
     */
    private static boolean isPattern(String reg, String value) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /***
     * 获取Float类型的随机数
     * @param min 最小值
     * @param max 最大值
     * @return 生成的随机数
     */
    public static float randomFloat(float min, float max) {
        // 直接调用Apache Commons Lang工具类
        return RandomUtils.nextFloat(min, max);
    }

    /***
     * 获取Double类型的随机数
     * @param min 最小值
     * @param max 最大值
     * @return 生成的随机数
     */
    public static double randomDouble(double min, double max) {
        // 直接调用Apache Commons Lang工具类
        return RandomUtils.nextDouble(min, max);
    }

    /***
     * 获取Long类型的随机数
     * @param min 最小值
     * @param max 最大值
     * @return 生成的随机数
     */
    public static long randomLong(long min, long max) {
        // 直接调用Apache Commons Lang工具类
        return RandomUtils.nextLong(min, max);
    }

    /***
     * 获取Integer类型的随机数
     * @param min 最小值
     * @param max 最大值
     * @return 生成的随机数
     */
    public static int randomInteger(int min, int max) {
        // 直接调用Apache Commons Lang工具类
        return RandomUtils.nextInt(min, max);
    }

    /**
     * 转换Float数为百分比数字
     *
     * @param value  百分比小数
     * @param dotNum 百分比位数
     * @return 百分比数字
     */
    public static String percentageFloat(float value, int dotNum) {
        String percent = getPercentString(dotNum);
        DecimalFormat decimalFormat = new DecimalFormat(percent);
        return decimalFormat.format(value);
    }

    /**
     * 转换Double数为百分比数字
     *
     * @param value  百分比小数
     * @param dotNum 百分比位数
     * @return 百分比数字
     */
    public static String percentageDouble(double value, int dotNum) {
        String percent = getPercentString(dotNum);
        DecimalFormat decimalFormat = new DecimalFormat(percent);
        return decimalFormat.format(value);
    }

    /**
     * 按照dotNum数量截取Double小数点，并返回截取后的小数
     * <p>double value = 3.1415926</p>
     * <p>double result = decimalDouble(value, 2)</p>
     * <p>result = 3.14</p>
     * <p>double value = 3.1415926</p>
     * <p>double result = decimalDouble(value, 4)</p>
     * <p>result = 3.1416</p>
     *
     * @param value  原始数
     * @param dotNum 截取的位数
     * @return 截取后的小数数值
     */
    public static double decimalDouble(double value, int dotNum) {
        String formatTemplate = setZero(dotNum);
        DecimalFormat format = new DecimalFormat(formatTemplate);
        String result = format.format(value);
        log.debug("after format result is {}", result);
        return Double.parseDouble(result);
    }

    /**
     * 按照dotNum数量截取Float小数点，并返回截取后的小数
     * <p>double value = 3.1415926</p>
     * <p>double result = decimalDouble(value, 2)</p>
     * <p>result = 3.14</p>
     * <p>double value = 3.1415926</p>
     * <p>double result = decimalDouble(value, 4)</p>
     * <p>result = 3.1416</p>
     *
     * @param value  原始数www
     * @param dotNum 截取的位数
     * @return 截取后的小数数值
     */
    public static float decimalFloat(float value, int dotNum) {
        String formatTemplate = setZero(dotNum);
        DecimalFormat format = new DecimalFormat(formatTemplate);
        String result = format.format(value);
        log.debug("after format result is {}", result);
        return Float.parseFloat(result);
    }

    /**
     * 判断输入的字符串是否为数字
     *
     * @param number 数字的字符串
     * @return true/false
     */
    public static boolean isNumber(String number) {
        String reg = "^(-?\\d+)(\\.\\d+)?$";
        return isPattern(reg, number);
    }

    /**
     * 判断输入的字符串是否为正整数
     *
     * @param number 数字的字符串
     * @return true/false
     */
    public static boolean isInteger(String number) {
        String reg = "^[0-9]*$";
        return isPattern(reg, number);
    }
}
