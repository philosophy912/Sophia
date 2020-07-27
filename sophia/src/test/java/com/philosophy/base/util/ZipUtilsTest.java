package com.philosophy.base.util;

import com.philosophy.BaseTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZipUtilsTest {
    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private Path testFolder;

    @BeforeEach
    void setUp() throws IOException {
        testFolder = Paths.get(RESOURCES + "\\test");
        FilesUtils.forceDeleteOnExit(testFolder.toFile());
        Files.createDirectories(testFolder);

    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.forceDeleteOnExit(testFolder.toFile());
    }


    @Test
    void compress() {
        Path file = Paths.get(RESOURCES + "\\1.jpg");
        Path zipFile = Paths.get(RESOURCES + "\\1.zip");
        assertTrue(ZipUtils.compress(file, zipFile, false));
        assertTrue(Files.exists(zipFile));
        Path folder = Paths.get(RESOURCES + "\\Downloads");
        Path zipFolder = Paths.get(testFolder.toAbsolutePath() + File.separator + "2.zip");
        assertTrue(ZipUtils.compress(folder, zipFolder, false));
        assertTrue(Files.exists(zipFolder));

    }

    @Test
    void testCompress() {
        Path path1 = Paths.get(RESOURCES + "\\1.jpg");
        Path path2 = Paths.get(RESOURCES + "\\2.jpg");
        Path path3 = Paths.get(RESOURCES + "\\3.jpg");
        List<Path> paths = Arrays.asList(path1, path2, path3);
        Path zipFile = Paths.get(testFolder.toAbsolutePath() + File.separator + "3.zip");
        assertTrue(ZipUtils.compress(paths, zipFile, false));
        assertTrue(Files.exists(zipFile));
    }

    @Test
    void decompress() {
        Path zipFile = Paths.get(RESOURCES + "\\1.zip");
        assertTrue(ZipUtils.decompress(zipFile, testFolder));
        Path unzipFile = Paths.get(testFolder.toAbsolutePath() + File.separator + "1.jpg");
        assertTrue(Files.exists(unzipFile));
    }
}