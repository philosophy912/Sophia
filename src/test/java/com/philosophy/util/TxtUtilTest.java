package com.philosophy.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TxtUtilTest {

    @Test
    public void read() {
    }

    @Test
    public void write() throws IOException {
        for (int i = 0; i < 1000; i++) {
            TxtUtil.write(Paths.get("d:\\aaa.txt"), "" + i);
        }
    }

    @Test
    public void write1() {
    }

    @Test
    public void write2() {
    }
}