package com.philosophy.excel.utils;

import com.philosophy.BaseTestUtils;
import com.philosophy.base.util.FilesUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ExcelUtilsTest {
    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private Path tempFile;
    private Path file;
    private String sheetName = "测试用例";
    private ExcelUtils excelUtils;

    @BeforeEach
    void setUp() throws IOException {
        excelUtils = new ExcelUtils();
        file = Paths.get(RESOURCES + "\\仪表自动化测试用例汇总_v1.4.xlsx");
        tempFile = Paths.get(RESOURCES + "\\temp.xls");
        FilesUtils.deleteFiles(tempFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.deleteFiles(tempFile);
        FilesUtils.deleteFiles(tempFile);
    }


    @Test
    void read() throws IOException {
        List<String[]> contents = excelUtils.read(file, sheetName);
        assertEquals(contents.size(), 231);
    }

    @Test
    void testRead() throws IOException {
        String content = excelUtils.read(file, sheetName, 16, 7);
        assertEquals("1.仪表不显示挡位", content);

    }

    @Test
    void write() throws IOException {
        String[] content = new String[]{"a", "b", "c", "d", "123", "12.3"};
        excelUtils.write(tempFile, sheetName, content);
        assertTrue(Files.exists(tempFile));
        List<String[]> contents = excelUtils.read(tempFile, sheetName);
        assertEquals(contents.size(), 1);
        String[] readContent = contents.get(0);
        assertEquals(content.length, readContent.length);
        for (int i = 0; i < content.length; i++) {
            assertEquals(content[i], readContent[i]);
        }
    }


    @Test
    void testWrite() throws IOException {
        int size = 10;
        List<String[]> contents = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String[] content = new String[size];
            for (int j = 0; j < size; j++) {
                content[j] = i + " : " + j;
            }
            contents.add(content);
        }
        excelUtils.write(tempFile, sheetName, contents);
        assertTrue(Files.exists(tempFile));
        List<String[]> readContents = excelUtils.read(tempFile, sheetName);
        assertEquals(readContents.size(), size);
        for (int i = 0; i < readContents.size(); i++) {
            String[] content = contents.get(i);
            String[] readContent = readContents.get(i);
            assertEquals(content.length, readContent.length);
            for (int j = 0; j < content.length; j++) {
                assertEquals(content[j], readContent[j]);
            }
        }
    }
}