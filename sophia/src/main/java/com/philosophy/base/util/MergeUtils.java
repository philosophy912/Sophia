package com.philosophy.base.util;

import java.util.Arrays;

/**
 * @author lizhe
 * @date 2019/10/11:16:44
 */
public class MergeUtils {
    /**
     * 基本数组转换之byte
     *
     * @param sources byte数组
     * @return byte数组
     */
    public static byte[] convert(Byte[] sources) {
        byte[] result = new byte[sources.length];
        int i = 0;
        for (Byte source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }
    /**
     * 基本数组转换之byte
     *
     * @param sources byte数组
     * @return byte数组
     */
    public static Byte[] convert(byte[] sources) {
        Byte[] result = new Byte[sources.length];
        int i = 0;
        for (byte source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之int
     *
     * @param sources int数组
     * @return int数组
     */
    public static int[] convert(Integer[] sources) {
        int[] result = new int[sources.length];
        int i = 0;
        for (Integer source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }
    /**
     * 基本数组转换之int
     *
     * @param sources int数组
     * @return int数组
     */
    public static Integer[] convert(int[] sources) {
        Integer[] result = new Integer[sources.length];
        int i = 0;
        for (int source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之double
     *
     * @param sources double数组
     * @return double数组
     */
    public static double[] convert(Double[] sources) {
        double[] result = new double[sources.length];
        int i = 0;
        for (Double source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }
    /**
     * 基本数组转换之double
     *
     * @param sources double数组
     * @return double数组
     */
    public static Double[] convert(double[] sources) {
        Double[] result = new Double[sources.length];
        int i = 0;
        for (double source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之float
     *
     * @param sources float数组
     * @return float数组
     */
    public static float[] convert(Float[] sources) {
        float[] result = new float[sources.length];
        int i = 0;
        for (Float source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }
    /**
     * 基本数组转换之float
     *
     * @param sources float数组
     * @return float数组
     */
    public static Float[] convert(float[] sources) {
        Float[] result = new Float[sources.length];
        int i = 0;
        for (float source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之short
     *
     * @param sources short数组
     * @return short数组
     */
    public static short[] convert(Short[] sources) {
        short[] result = new short[sources.length];
        int i = 0;
        for (Short source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之short
     *
     * @param sources short数组
     * @return short数组
     */
    public static Short[] convert(short[] sources) {
        Short[] result = new Short[sources.length];
        int i = 0;
        for (short source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之long
     *
     * @param sources long数组
     * @return long数组
     */
    public static long[] convert(Long[] sources) {
        long[] result = new long[sources.length];
        int i = 0;
        for (Long source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }
    /**
     * 基本数组转换之long
     *
     * @param sources long数组
     * @return long数组
     */
    public static Long[] convert(long[] sources) {
        Long[] result = new Long[sources.length];
        int i = 0;
        for (long source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之char
     *
     * @param sources char数组
     * @return char数组
     */
    public static char[] convert(Character[] sources) {
        char[] result = new char[sources.length];
        int i = 0;
        for (Character source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }
    /**
     * 基本数组转换之char
     *
     * @param sources char数组
     * @return char数组
     */
    public static Character[] convert(char[] sources) {
        Character[] result = new Character[sources.length];
        int i = 0;
        for (char source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之boolean
     *
     * @param sources boolean数组
     * @return boolean数组
     */
    public static boolean[] convert(Boolean[] sources) {
        boolean[] result = new boolean[sources.length];
        int i = 0;
        for (Boolean source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 基本数组转换之boolean
     *
     * @param sources boolean数组
     * @return boolean数组
     */
    public static Boolean[] convert(boolean[] sources) {
        Boolean[] result = new Boolean[sources.length];
        int i = 0;
        for (boolean source : sources) {
            result[i] = source;
            i++;
        }
        return result;
    }

    /**
     * 合并数组（泛型）如果是基本类型请做类转换
     *
     * @param t   原始数组
     * @param ts  需要合并的数组
     * @param <T> 泛型
     * @return 合并后的数组
     */
    @SafeVarargs
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
}
