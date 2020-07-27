package com.philosophy.base.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringsUtilsTest {
    @Test
    void test() {
        assertEquals("", StringsUtils.trimToEmpty(null));
    }
}