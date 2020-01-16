package com.philosophy.base.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @author lizhe
 * @date 2019/10/10:14:52
 */
@Slf4j
public class ParseUtils {
    /**
     * 将字节数组转换成为HEX的字符串
     * <p>[72,11,3,4,5] -> 480B030405</p>
     *
     * @param bytes 字节数组
     * @return 转换完成的HEX字符串
     */
    public static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将HEX字符串转换成字节数组
     * <p> 480B030405 -> [72,11,3,4,5]</p>
     *
     * @param string HEX字符串
     * @return 转换后的字节数组
     */
    public static byte[] stringToBytes(String string) {
        byte[] result = null;
        if (string.length() > 1) {
            result = new byte[string.length() / 2];
            for (int i = 0; i < result.length; i++) {
                int high = Integer.parseInt(string.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(string.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
        }
        return result;
    }

    /**
     * 将Byte转换成int[]数组，分别对应0-7位的值
     *
     * @param b byte字节
     * @return 返回int[]数组，每个数组值对应一个位
     */
    public static int[] byteToBit(byte b) {
        int[] bits = new int[8];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = (b >> i) & 0x1;
        }
        return bits;
    }

    /**
     * 转换集合为String
     *
     * @param collection 集合对象
     * @return 转换后的字符串
     */
    public static String to(Collection<String> collection) {
        StringBuilder sb = new StringBuilder();
        for (String s : collection) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 以分隔符转换成String
     *
     * @param collection 集合对象
     * @param split      分隔符
     * @return 转换后的字符串
     */
    public static String to(Collection<String> collection, String split) {
        StringBuilder sb = new StringBuilder();
        for (String s : collection) {
            sb.append(s).append(split);
        }
        return sb.toString();
    }

    /**
     * 字符串集合转换成字符串数组
     *
     * @param collection 字符串集合
     * @return 字符串数组
     */
    public static String[] toArray(Collection<String> collection) {
        int size = collection.size();
        return collection.toArray(new String[size]);
    }


}
