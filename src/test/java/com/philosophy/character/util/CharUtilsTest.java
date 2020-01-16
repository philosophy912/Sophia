package com.philosophy.character.util;

import com.philosophy.base.util.NumericUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharUtilsTest {


    @Test
    void setWindowsSupport() {
        String[] strings = new String[]{"abcdefg", "abcdefg\\", "abc:defg/", "ab*cd?e\"fg", "abc<>|de?fg"};
        for (int i = 0; i < strings.length; i++) {
            int len1 = CharUtils.setWindowsSupport(strings[i]).length();
            int len2 = strings[i].length();
            int diff = len2 - len1;
            assertEquals(i, diff);
        }
    }

    @Test
    void setPrefixNumber() {
        int testCycle = 100;
        for (int i = 0; i < testCycle; i++) {
            int index = NumericUtils.randomInteger(3, 9);
            for (int j = 0; j < testCycle; j++) {
                int total = NumericUtils.randomInteger(0,1000000);
                String prefix = CharUtils.setPrefixNumber(index, total);
                int length = String.valueOf(total).length();
                int value = Integer.parseInt(prefix);
                assertEquals(length, prefix.length());
                assertEquals(index, value);
            }
        }
    }
}