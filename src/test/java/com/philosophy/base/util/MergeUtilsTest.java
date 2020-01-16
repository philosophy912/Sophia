package com.philosophy.base.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MergeUtilsTest {


    @Test
    void convert() {
        Byte[] source = new Byte[]{0x1, 0x2, 0x3, 0x4};
        byte[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(source[i], target[i]);
        }
        Byte[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void testConvert() {
        Integer[] source = new Integer[]{1, 2, 3, 4, 5};
        int[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(source[i], target[i]);
        }
        Integer[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void testConvert1() {
        Double[] source = new Double[]{1d, 2d, 3d, 4d, 5d};
        double[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(new BigDecimal(source[i]), new BigDecimal(target[i]));
        }
        Double[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void testConvert2() {
        Float[] source = new Float[]{1f, 2f, 3f, 4f, 5f};
        float[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(new BigDecimal(source[i]), new BigDecimal(target[i]));
        }
        Float[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void testConvert3() {
        Short[] source = new Short[]{1, 2, 3, 4, 5};
        short[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(source[i], target[i]);
        }
        Short[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void testConvert4() {
        Long[] source = new Long[]{1L, 2L, 3L, 4L, 5L};
        long[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(source[i], target[i]);
        }
        Long[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void testConvert5() {
        Character[] source = new Character[]{'a', 'b', 'c', 'd', 'e' };
        char[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(source[i], target[i]);
        }
        Character[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void testConvert6() {
        Boolean[] source = new Boolean[]{true, false, true, false, false, true, true};
        boolean[] target = MergeUtils.convert(source);
        for (int i = 0; i < source.length; i++) {
            assertEquals(source[i], target[i]);
        }
        Boolean[] src = MergeUtils.convert(target);
        for (int i = 0; i < source.length; i++) {
            assertEquals(src[i], target[i]);
        }
    }

    @Test
    void merge() {
        Integer[] source1 = new Integer[]{1, 2, 3, 4, 5};
        Integer[] source2 = new Integer[]{1, 2, 3, 4, 5};
        int[] source3 = new int[]{1, 2, 3, 4, 5, 6, 6};
        int[] source4 = new int[]{1, 2, 3, 4, 5, 7};
        Integer[] target1 = MergeUtils.merge(source1, source2);
        Integer[] target2 = MergeUtils.merge(MergeUtils.convert(source3), MergeUtils.convert(source4));
        assertEquals(target1.length, source1.length + source2.length);
        assertEquals(target2.length, source3.length + source4.length);

    }
}