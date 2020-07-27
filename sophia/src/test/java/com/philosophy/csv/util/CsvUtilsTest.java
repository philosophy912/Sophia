package com.philosophy.csv.util;

import com.philosophy.BaseTestUtils;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.csv.api.CsvEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CsvUtilsTest {

    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private static String GBK = "GBK";
    private Path csvFile;
    private Path tempFile;
    private CsvUtils csvUtils;

    @BeforeEach
    void setUp() {
        csvUtils = new CsvUtils();
        csvFile = Paths.get(RESOURCES + "\\123.csv");
        tempFile = Paths.get(RESOURCES + "\\temp.csv");
    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.deleteFiles(tempFile);
    }

    @Test
    void readTitle() throws Exception {
        String[] title = csvUtils.readTitle(csvFile, GBK, CsvEnum.DEFAULT);
        assertEquals(title.length, 35);
        assertEquals(title[2], "所属模块");
    }

    @Test
    void readContent() throws Exception {
        List<String[]> contents = csvUtils.readContent(csvFile, GBK, CsvEnum.DEFAULT);
        assertEquals(contents.size(), 147);
    }

    @Test
    void read() throws Exception {
        String content = csvUtils.read(csvFile, GBK, CsvEnum.DEFAULT, "Bug编号", 10);
        assertEquals(content, "15123");
    }

    @Test
    void testRead() throws IOException {
        String content = csvUtils.read(csvFile, GBK, CsvEnum.DEFAULT, 19, 9);
        assertEquals(content, "宋江波");
    }

    @Test
    void testRead1() throws Exception {
        List<String[]> contents = csvUtils.read(csvFile, GBK, CsvEnum.DEFAULT);
        assertEquals(contents.size(), 148);
    }

    @Test
    void write() throws Exception {
        FilesUtils.deleteFiles(tempFile);
        String[] contents = csvUtils.readTitle(csvFile, GBK, CsvEnum.DEFAULT);
        csvUtils.write(tempFile, GBK, CsvEnum.DEFAULT, false, contents);
        assertTrue(Files.exists(tempFile));
    }

    @Test
    void testWrite() throws Exception {
        FilesUtils.deleteFiles(tempFile);
        List<String[]> contents = csvUtils.read(csvFile, GBK, CsvEnum.DEFAULT);
        csvUtils.write(tempFile, GBK, CsvEnum.DEFAULT, false, contents);
        assertTrue(Files.exists(tempFile));
    }
}