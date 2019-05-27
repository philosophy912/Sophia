package com.philosophy.tools;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:13
 **/
public class Numeric {
    /**
     *
     */
    private static Random rand;

    private Numeric() {
        rand = new Random();
    }


    /**
     * 获取<code>Double</code>类型的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 返回最小值和最大值之间的某个数
     * @throws IllegalArgumentException 当传入的max小于min的时候抛出ParamInvalidException异常
     */
    public static double random(double min, double max) throws IllegalArgumentException {
        if (max < min) {
            throw new IllegalArgumentException(min + " is more than " + max);
        } else if (min == max) {
            return min;
        } else {
            return rand.nextDouble() * (max - min) + min;
        }
    }

    /**
     * 获取<code>Long</code>类型的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 返回最小值和最大值之间的某个数
     * @throws IllegalArgumentException 当传入的max小于min的时候抛出ParamInvalidException异常
     */
    public static long random(long min, long max) throws IllegalArgumentException {
        if (max < min) {
            throw new IllegalArgumentException(min + " is more than " + max);
        } else if (min == max) {
            return min;
        } else {
            return rand.nextLong() * (max - min) + min;
        }
    }

    /**
     * 转换小数<code>Long</code>为百分比数
     * <p>
     * 如：<b>0.85</b>转换的结果是<b>85%</b>
     *
     * @param value  传入的数值
     * @param dotNum 保留小数点的位数
     * @return 转换后的值
     */
    public static String percentage(double value, int dotNum) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(dotNum);
        return format.format(value);
    }

    /**
     * 按照位数截取小数点，并返回截取后的小数
     * <p>
     * 例：
     * <p>
     * 3.1415926 截取2位，返回3.14
     * <p>
     * 3.1415926 截取4位，返回3.1416
     *
     * @param value  要截取的数值
     * @param dotNum 截取的位数
     * @return 截取后的小数数值
     */
    public static double decimal(double value, int dotNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("0.");
        for (int i = 0; i < dotNum; i++) {
            sb.append("0");
        }
        DecimalFormat format = new DecimalFormat(sb.toString());
        String result = format.format(value);
        return Double.parseDouble(result);
    }


    private static boolean isPattern(String pattern, String value) {
        Pattern patt = Pattern.compile(pattern);
        Matcher matcher = patt.matcher(value);
        return matcher.matches();
    }

    /**
     * 判断输入的字符串是否为数字
     *
     * @param str 字符串
     * @return 当该字符串为数字时候返回真，否则返回假
     */
    public static boolean isNumber(String str) {
        String patt = "^(-?\\d+)(\\.\\d+)?$";
        return isPattern(patt, str);
    }

    /**
     * 判断输入的字符串是否是正整数
     *
     * @param str 字符串
     * @return 当该字符串为正整数时候返回真，否则返回假
     */
    public static boolean isInteger(String str) {
        String patt = "^[0-9]*$";
        return isPattern(patt, str);
    }
}
