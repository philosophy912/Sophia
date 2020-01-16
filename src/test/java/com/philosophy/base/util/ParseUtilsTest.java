package com.philosophy.base.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParseUtilsTest {

    @Test
    void bytesToString() {
        byte[] bytes = new byte[]{72, 11, 3, 4, 5};
        String hexBytes = "480B030405";
        assertEquals(hexBytes, ParseUtils.bytesToString(bytes));
    }

    @Test
    void stringToBytes() {
        byte[] bytes = new byte[]{72, 11, 3, 4, 5};
        String hexBytes = "480B030405";
        byte[] convert = ParseUtils.stringToBytes(hexBytes);
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bytes[i], convert[i]);
        }
    }

    @Test
    void byteToBit() {
        byte b = 0;
        int[] converts = ParseUtils.byteToBit(b);
        assertEquals(8, converts.length);
        for (int convert : converts) {
            assertEquals(0, convert);
        }

    }

    @Test
    void to() {
        List<String> list = Arrays.asList("a", "b", "c");
        assertEquals("abc", ParseUtils.to(list));
    }

    @Test
    void testTo() {
        List<String> list = Arrays.asList("a", "b", "c");
        assertEquals("a-b-c-", ParseUtils.to(list, "-"));
    }

    @Test
    void toArray() {
        List<String> list = Arrays.asList("a", "b", "c");
        String[] array = ParseUtils.toArray(list);
        assertEquals(list.size(), array.length);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), array[i]);
        }
    }
}