package com.philosophy.image.util;

import com.philosophy.BaseTestUtils;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.image.api.CompareEnum;
import com.philosophy.image.common.Picture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilsTest {
    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private Path originImage;
    private Path targetImage;
    private Path pngImg;
    private Path img1;
    private Path img2;
    private Path img3;

    @BeforeEach
    void setUp() {
        originImage = Paths.get(RESOURCES + "\\052.JPG_4200x3600_24.jpg");
        targetImage = Paths.get(RESOURCES + "\\target.jpg");
        pngImg = Paths.get(RESOURCES + "\\target.png");
        img1 = Paths.get(RESOURCES + "\\1.jpg");
        img2 = Paths.get(RESOURCES + "\\2.jpg");
        img3 = Paths.get(RESOURCES + "\\3.jpg");
    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.deleteFiles(targetImage, pngImg);
    }

    @Test
    void testSize() throws IOException {
        Map<Integer, Integer> size = ImageUtils.size(originImage);
        assertEquals(size.get(Picture.WIDTH), 4200);
        assertEquals(size.get(Picture.HEIGHT), 3600);

    }

    @Test
    void scale() throws IOException {
        FilesUtils.deleteFiles(targetImage);
        Map<Integer, Integer> origin = ImageUtils.size(originImage);
        int scale = 2;
        ImageUtils.scale(originImage, targetImage, scale, false);
        assertTrue(Files.exists(targetImage));
        Map<Integer, Integer> target = ImageUtils.size(targetImage);
        assertEquals(origin.get(Picture.WIDTH) / scale, target.get(Picture.WIDTH));
        assertEquals(origin.get(Picture.HEIGHT) / scale, target.get(Picture.HEIGHT));
    }

    @Test
    void testScale() throws IOException {
        FilesUtils.deleteFiles(targetImage);
        Map<Integer, Integer> origin = ImageUtils.size(originImage);
        int size = 3;
        int height = origin.get(Picture.HEIGHT) / size;
        int width = origin.get(Picture.WIDTH) / size;
        ImageUtils.scale(originImage, targetImage, height, width, false);
        assertTrue(Files.exists(targetImage));
        Map<Integer, Integer> target = ImageUtils.size(targetImage);
        assertEquals(width, target.get(Picture.WIDTH));
        assertEquals(height, target.get(Picture.HEIGHT));
    }

    @Test
    void cut() throws IOException {
        FilesUtils.deleteFiles(targetImage);
        int width = 1000;
        int height = 1000;
        BufferedImage target = ImageUtils.cut(originImage, 200, 1200, width, height);
        ImageUtils.write(target, targetImage);
        assertTrue(Files.exists(targetImage));
        Map<Integer, Integer> targetSize = ImageUtils.size(targetImage);
        assertEquals(targetSize.get(Picture.HEIGHT), height);
        assertEquals(targetSize.get(Picture.WIDTH), width);

    }

    @Test
    void convert() throws IOException {
        FilesUtils.deleteFiles(pngImg);
        ImageUtils.convert(originImage, pngImg);
        assertTrue(Files.exists(pngImg));
    }

    @Test
    void gray() throws IOException {
        FilesUtils.deleteFiles(targetImage);
        ImageUtils.gray(originImage, targetImage);
        assertTrue(Files.exists(targetImage));
    }

    @Test
    void compare() throws IOException {
        double result1 = ImageUtils.compare(CompareEnum.HISTOGRAM, img1, img2);
        double result2 = ImageUtils.compare(CompareEnum.HISTOGRAM, img1, img3);
        assertTrue(result1 > 0.99);
        assertFalse(result2 > 0.99);
        double result3 = ImageUtils.compare(CompareEnum.PIXEL, img1, img2);
        double result4 = ImageUtils.compare(CompareEnum.PIXEL, img1, img3);
        assertTrue(result3 > 0.99);
        assertFalse(result4 > 0.99);
        double result5 = ImageUtils.compare(CompareEnum.HAMMING, img1, img2);
        double result6 = ImageUtils.compare(CompareEnum.HAMMING, img1, img3);
        assertTrue(result5 < 1);
        assertFalse(result6 < 1);
    }


}