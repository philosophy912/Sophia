package com.philosophy.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:17
 **/
public class Parse {

    private static final String UTF8 = "UTF-8";

    private Parse() {
    }

    /**
     * 将字节数组转换成为HEX的字符串
     * <br>
     * 如：[72,11,3,4,5]转换完成后变成 480B030405
     *
     * @param buff 字节数组
     * @return 转换完成的HEX字符串
     */
    public static String toString(byte[] buff) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buff) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将HEX字符串转换成字节数组
     * <br>
     * 如480B030405 转换后的byte数组为 [72, 11, 3, 4, 5]
     *
     * @param str HEX字符串
     * @return 转换后的字节数组
     */
    public static byte[] toBytes(String str) {
        byte[] result = null;
        if (str.length() > 1) {
            result = new byte[str.length() / 2];
            for (int i = 0; i < str.length() / 2; i++) {
                int high = Integer.parseInt(str.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(str.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
        }
        return result;
    }

    /**
     * 将Byte转换成int[]数组，分别对应0-7位的值。
     *
     * @param b byte字节
     * @return 返回int[]数组，每个数组值对应一个位
     */
    public static int[] toBit(byte b) {
        int[] bits = new int[8];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = (b >> i) & 0x1;
        }
        return bits;
    }

    /**
     * 转换集合为数组
     *
     * @param lists 集合T
     * @param <T>   泛型
     * @return 返回集合生成的数组
     */
    public static <T> T[] toArray(Collection<T> lists) {
        T[] ts = (T[]) lists.stream().toArray();
        return ts;
    }

    /**
     * 转换集合为String
     *
     * @param lists 集合对象
     * @param <T>   泛型
     * @return 转换后的字符串
     */
    public static <T> String toString(Collection<T> lists) {
        return Arrays.toString(toArray(lists));
    }

    /**
     * 转换集合为String
     *
     * @param lists  集合对象
     * @param splite 分隔符
     * @param <T>    泛型
     * @return 转换后的字符串
     */
    public static <T> String toString(Collection<T> lists, String splite) {
        StringBuilder sb = new StringBuilder();
        for (T t : lists) {
            sb.append(t.toString() + splite);
        }
        return sb.toString();
    }

    /**
     * 转换集合数组成集合
     *
     * @param lists 集合数组
     * @param <T>   泛型
     * @return 转换后的集合
     */
    public static <T> Collection<T> toList(Collection<T[]> lists) {
        Collection<T> result = new LinkedList<>();
        for (T[] t : lists) {
            result.add((T) t);
        }
        return result;
    }

    /**
     * 十六进制的字符串如0x23变成long形,
     * <p>只能设置十六进制的数字，如23，不需要加0X
     * <p>如ff,转换后的结果是255，
     * <p>如传入0xff,则返回-1
     *
     * @param str 十六进制的字符串
     * @return long形的数值
     */
    public static long toLong(String str) {
        long iToReturn = 0;
        int iExp = 0;
        char chByte;

        // The string to convert is empty
        if (str.equals(""))
            return 0;
        // The string have more than 8 character (the equivalent value
        // exeeds the DWORD capacyty
        if (str.length() > 8)
            return 0;
        // We convert any character to its Upper case
        str = str.toUpperCase();
        try {
            // We calculate the number using the Hex To Decimal formula
            for (int i = str.length() - 1; i >= 0; i--) {
                chByte = (char) str.getBytes(UTF8)[i];
                switch ((int) chByte) {
                    case 65:
                        iToReturn += (long) (10 * Math.pow(16.0f, iExp));
                        break;
                    case 66:
                        iToReturn += (long) (11 * Math.pow(16.0f, iExp));
                        break;
                    case 67:
                        iToReturn += (long) (12 * Math.pow(16.0f, iExp));
                        break;
                    case 68:
                        iToReturn += (long) (13 * Math.pow(16.0f, iExp));
                        break;
                    case 69:
                        iToReturn += (long) (14 * Math.pow(16.0f, iExp));
                        break;
                    case 70:
                        iToReturn += (long) (15 * Math.pow(16.0f, iExp));
                        break;
                    default:
                        if ((chByte < 48) || (chByte > 57))
                            return -1;
                        iToReturn += (long) Integer.parseInt(((Character) chByte).toString()) * Math.pow(16.0f, iExp);
                        break;
                }
                iExp++;
            }
        } catch (Exception ex) {
            return 0;
        }
        return iToReturn;
    }
}
