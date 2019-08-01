package com.philosophy.image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class HammingTest {

    private Hamming hamming;
    private String file1 = "src/test/resources/1.jpg";
    private String file2 = "src/test/resources/2.jpg";
    private String file3 = "src/test/resources/3.jpg";

    @Before
    public void setup() {
        hamming = new Hamming();
    }

    @Test
    public void compare() throws IOException {
        Path path1 = Paths.get(file1);
        Path path2 = Paths.get(file2);
        Path path3 = Paths.get(file3);
        double result1 = hamming.compare(path1, path2);
        double result2 = hamming.compare(path1, path3);
        assertEquals(0d, result1, 0.0);
        assertNotEquals(0d, result2);
    }
}