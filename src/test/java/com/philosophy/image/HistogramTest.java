package com.philosophy.image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class HistogramTest {
    private static Logger log = LogManager.getLogger(HistogramTest.class);

    private String file1 = "src/test/resources/1.jpg";
    private String file2 = "src/test/resources/2.jpg";
    private String file3 = "src/test/resources/3.jpg";

    @Test
    public void compare() throws IOException {
        Histogram histogram = new Histogram();
        Path path1 = Paths.get(file1);
        Path path2 = Paths.get(file2);
        Path path3 = Paths.get(file3);
        double result1 = histogram.compare(path1, path2);
        double result2 = histogram.compare(path1, path3);
        log.debug("result1[{}], result2[{}]",result1, result2);
        assertEquals(1d, result1, 0.001);
        assertNotEquals(1d, result2);
    }
}