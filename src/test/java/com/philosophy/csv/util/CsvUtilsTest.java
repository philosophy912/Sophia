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

import static org.junit.jupiter.api.Assertions.*;

class CsvUtilsTest {

    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private static String GBK = "GBK";
    private Path csvFile;
    private Path tempFile;

    @BeforeEach
    void setUp() {
        csvFile = Paths.get(RESOURCES + "\\123.csv");
        tempFile = Paths.get(RESOURCES + "\\temp.csv");
    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.deleteFiles(tempFile);
    }

    @Test
    void readTitle() throws Exception {
        String[] title = CsvUtils.readTitle(csvFile, GBK, CsvEnum.DEFAULT);
        assertEquals(title.length, 35);
        assertEquals(title[2], "所属模块");
    }

    @Test
    void readContent() throws Exception {
        List<String[]> contents = CsvUtils.readContent(csvFile, GBK, CsvEnum.DEFAULT);
        assertEquals(contents.size(), 147);
    }

    @Test
    void read() throws Exception {
        String content = CsvUtils.read(csvFile, GBK, CsvEnum.DEFAULT, "Bug编号", 10);
        assertEquals(content, "15123");
    }

    @Test
    void testRead() throws IOException {
        String content = CsvUtils.read(csvFile, GBK, CsvEnum.DEFAULT, 19, 9);
        assertEquals(content, "宋江波");
    }

    @Test
    void testRead1() throws Exception {
        List<String[]> contents = CsvUtils.read(csvFile, GBK, CsvEnum.DEFAULT);
        assertEquals(contents.size(), 148);
    }

    @Test
    void write() throws Exception {
        FilesUtils.deleteFiles(tempFile);
        String[] contents = CsvUtils.readTitle(csvFile, GBK, CsvEnum.DEFAULT);
        CsvUtils.write(tempFile, GBK, CsvEnum.DEFAULT, false, contents);
        assertTrue(Files.exists(tempFile));
    }

    @Test
    void testWrite() throws Exception {
        FilesUtils.deleteFiles(tempFile);
        List<String[]> contents = CsvUtils.read(csvFile, GBK, CsvEnum.DEFAULT);
        CsvUtils.write(tempFile, GBK, CsvEnum.DEFAULT, false, contents);
        assertTrue(Files.exists(tempFile));
    }
}