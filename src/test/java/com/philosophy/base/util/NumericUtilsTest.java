package com.philosophy.base.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumericUtilsTest {


    @Test
    void randomFloat() {
        float min = 5.0f;
        float max = 7.0f;
        float result = NumericUtils.randomFloat(min, max);
        assertTrue(result >= min);
        assertTrue(result <= max);
    }

    @Test
    void randomDouble() {
        double min = 5.0d;
        double max = 7.0d;
        double result = NumericUtils.randomDouble(min, max);
        assertTrue(result >= min);
        assertTrue(result <= max);
    }

    @Test
    void randomLong() {
        long min = 5L;
        long max = 7L;
        long result = NumericUtils.randomLong(min, max);
        assertTrue(result <= max);
        assertTrue(result >= min);
    }


    @Test
    void randomInteger() {
        int min = 5;
        int max = 7;
        int result = NumericUtils.randomInteger(min, max);
        assertTrue(result >= min);
        assertTrue(result <= max);
    }

    @Test
    void percentageFloat() {
        float value = 0.35246323232323f;
        String result1 = NumericUtils.percentageFloat(value, 2);
        assertEquals("35.25%", result1);
        String result2 = NumericUtils.percentageFloat(value, 4);
        assertEquals("35.2463%", result2);
    }

    @Test
    void percentageDouble() {
        double value = 0.35246323232323d;
        String result1 = NumericUtils.percentageDouble(value, 2);
        assertEquals("35.25%", result1);
        String result2 = NumericUtils.percentageDouble(value, 4);
        assertEquals("35.2463%", result2);
    }

    @Test
    void decimalDouble() {
        double value = 13.1415926d;
        int dotNum1 = 2;
        int dotNum2 = 4;
        double result1 = NumericUtils.decimalDouble(value, dotNum1);
        double result2 = NumericUtils.decimalDouble(value, dotNum2);
        assertEquals(13.14d, result1);
        assertEquals(13.1416d, result2);
    }

    @Test
    void decimalFloat() {
        float value = 13.1415926f;
        int dotNum1 = 2;
        int dotNum2 = 4;
        float result1 = NumericUtils.decimalFloat(value, dotNum1);
        float result2 = NumericUtils.decimalFloat(value, dotNum2);
        assertEquals(13.14f, result1);
        assertEquals(13.1416f, result2);

    }

    @Test
    void isNumber() {
        String str1 = "0.1234";
        String str2 = "0.123sdf4";
        assertTrue(NumericUtils.isNumber(str1));
        assertFalse(NumericUtils.isNumber(str2));
    }

    @Test
    void isInteger() {
        String str1 = "1234";
        String str2 = "123.4";
        assertTrue(NumericUtils.isInteger(str1));
        assertFalse(NumericUtils.isInteger(str2));
    }
}