package com.philosophy.tools;

import java.util.Arrays;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:16
 **/
public class Merge {
    private Merge() {
    }

    /**
     * 合并数组（泛型）如果是基本类型请做类转换
     *
     * @param t   原始数组
     * @param ts  需要合并的数组
     * @param <T> 泛型
     * @return 合并后的数组
     */
    public static <T> T[] merge(T[] t, T[]... ts) {
        int total = t.length;
        for (T[] e : ts) {
            total += e.length;
        }
        T[] result = Arrays.copyOf(t, total);
        int offset = t.length;
        for (T[] e : ts) {
            System.arraycopy(e, 0, result, offset, e.length);
            offset += e.length;
        }
        return result;
    }

    /**
     * 基本数组转换之byte
     *
     * @param source byte数组
     * @return
     */
    public static byte[] convert(Byte[] source) {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    /**
     * 基本数组转换之int
     *
     * @param source int数组
     * @return
     */
    public static int[] convert(Integer[] source) {
        int[] result = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    /**
     * 基本数组转换之double
     *
     * @param source double数组
     * @return
     */
    public static double[] convert(Double[] source) {
        double[] result = new double[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    /**
     * 基本数组转换之float
     *
     * @param source float数组
     * @return
     */
    public static float[] convert(Float[] source) {
        float[] result = new float[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    /**
     * 基本数组转换之short
     *
     * @param source short数组
     * @return
     */
    public static short[] convert(Short[] source) {
        short[] result = new short[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    /**
     * 基本数组转换之long
     *
     * @param source long数组
     * @return
     */
    public static long[] convert(Long[] source) {
        long[] result = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    /**
     * 基本数组转换之char
     *
     * @param source char数组
     * @return
     */
    public static char[] convert(Character[] source) {
        char[] result = new char[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    /**
     * 基本数组转换之boolean
     *
     * @param source boolean数组
     * @return
     */
    public static boolean[] convert(Boolean[] source) {
        boolean[] result = new boolean[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }
}
