package com.philosophy.image.util;

import com.philosophy.image.api.CompareEnum;
import com.philosophy.image.common.Picture;
import com.philosophy.image.factory.ImageCompareFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author lizhe
 * @date 2019/10/12:16:48
 */
public class ImageUtils {

    private Picture picture = new Picture();


    /**
     * 缩放图像（按比例缩放）
     *
     * @param source 源图片
     * @param target 目标图片
     * @param scale  缩放比例
     * @param flag   缩放选择:true放大false缩小
     */
    public void scale(Path source, Path target, int scale, boolean flag) throws IOException {
        BufferedImage bi = picture.read(source);
        picture.scale(bi, target, scale, flag);
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param source 源图片
     * @param target 目标图片
     * @param height 缩放后的高度
     * @param width  缩放后的宽度
     * @param flag   比例不对时是否需要补白:true为补白,false为不补白
     * @throws IOException 抛出异常
     */
    public void scale(Path source, Path target, int height, int width, boolean flag) throws IOException {
        BufferedImage bi = picture.read(source);
        picture.scale(bi, target, height, width, flag);
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)[BMP, JPG, PNG, JPEG, GIF]
     *
     * @param source 源图片
     * @param x      目标切片起点坐标X
     * @param y      目标切片起点坐标Y
     * @param width  目标切片宽度
     * @param height 目标切片高度
     * @return 切割后的图像BufferedImage对象
     */
    public BufferedImage cut(Path source, int x, int y, int width, int height) throws IOException {
        return picture.cut(source, x, y, width, height);
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param source 源图片
     * @param target 目标图片
     */
    public void convert(Path source, Path target) throws IOException {
        BufferedImage bi = picture.read(source);
        picture.convert(bi, target);
    }

    /**
     * 彩色转为黑白
     *
     * @param source 源图片
     * @param target 目标图片
     */
    public void gray(Path source, Path target) throws IOException {
        BufferedImage bi = picture.read(source);
        picture.gray(bi, target);
    }

    /**
     * 获取图片大小(宽x高)
     *
     * @param image 图片
     * @return 宽高数组[Width, Height]
     */
    public Map<Integer, Integer> size(Path image) throws IOException {
        BufferedImage bi = picture.read(image);
        return picture.size(bi);
    }

    /**
     * 图像对比
     * 支持汉明距，直方图，像素
     *
     * @param compareEnum 对比类型
     * @param image1      图片1
     * @param image2      图片2
     * @return 相似度（汉明距返回的是整数）
     * @throws IOException IO异常
     */
    public double compare(CompareEnum compareEnum, Path image1, Path image2) throws IOException {
        BufferedImage bi1 = picture.read(image1);
        BufferedImage bi2 = picture.read(image2);
        return ImageCompareFactory.createCompare(compareEnum).compare(bi1, bi2);
    }

    /**
     * BufferedImage对象写入文件
     *
     * @param bi   BufferedImage对象
     * @param path 文件地址
     * @throws IOException IO异常
     */
    public void write(BufferedImage bi, Path path) throws IOException {
        picture.write(bi, path);
    }

}
