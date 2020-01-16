package com.philosophy.txt.util;

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

import static org.junit.jupiter.api.Assertions.*;

class TxtUtilsTest {
    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private Path txt;
    private String charset = "UTF-8";
    private boolean isWrap = true;
    private boolean isAppend = false;
    private Path testTxt;

    @BeforeEach
    void setUp() throws IOException {
        txt = Paths.get(RESOURCES + "\\test.txt");
        testTxt = Paths.get(RESOURCES + "\\normal.txt");
        FilesUtils.deleteFiles(testTxt);
    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.deleteFiles(testTxt);
    }

    @Test
    void read() throws IOException {
        List<String> contents = TxtUtils.read(txt, charset, true);
        int i = 1;
        for (String s : contents) {
            assertEquals(String.valueOf(i), s);
            i++;
        }
    }

    @Test
    void write() throws IOException {
        FilesUtils.deleteFiles(testTxt);
        String content = "adslfjdlskjfd";
        TxtUtils.write(testTxt, content, charset, isAppend, isWrap);
        assertTrue(Files.exists(testTxt));
        List<String> contents = TxtUtils.read(testTxt, charset, true);
        assertEquals(1, contents.size());
        assertEquals(content, contents.get(0));

    }

    @Test
    void testWrite() throws IOException {
        FilesUtils.deleteFiles(testTxt);
        String[] content = new String[]{"adslfjdlskjfd", "adslfjdlskjfd"};
        TxtUtils.write(testTxt, content, charset, isAppend, isWrap);
        assertTrue(Files.exists(testTxt));
        List<String> contents = TxtUtils.read(testTxt, charset, true);
        assertEquals(2, contents.size());
        for (int i = 0; i < contents.size(); i++) {
            assertEquals(content[i], contents.get(i));
        }
    }

    @Test
    void testWrite1() throws IOException {
        FilesUtils.deleteFiles(testTxt);
        List<String[]> contentList = new ArrayList<>();
        contentList.add(new String[]{"sfsdfsdf"});
        contentList.add(new String[]{"adslfjdlskjfd"});
        contentList.add(new String[]{"sewerer"});
        TxtUtils.write(testTxt, contentList, charset, isAppend, isWrap);
        assertTrue(Files.exists(testTxt));
        List<String> contents = TxtUtils.read(testTxt, charset, false);
        assertEquals(3, contents.size());
        for (int i = 0; i < contents.size(); i++) {
            assertEquals(contentList.get(i)[0], contents.get(i));
        }
    }
}