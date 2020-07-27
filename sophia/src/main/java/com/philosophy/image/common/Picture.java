package com.philosophy.image.common;


import com.philosophy.base.common.Closee;
import com.philosophy.base.util.FilesUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author lizhe
 */
public class Picture {
    public static final int HEIGHT = 0;
    public static final int WIDTH = 1;

    /**
     * 读取图片文件
     *
     * @param path 文件地址
     * @return 图片的BufferedImage对象
     * @throws IOException 抛出异常
     */
    public BufferedImage read(Path path) throws IOException {
        return ImageIO.read(Files.newInputStream(path));
    }

    /**
     * BufferedImage对象写入文件
     *
     * @param bi   BufferedImage对象
     * @param path 文件地址
     * @throws IOException 当文件地址不是文件的时候抛出异常
     * @throws IOException 抛出异常
     */
    public void write(BufferedImage bi, Path path) throws IOException {
        String ext = FilesUtils.getExtension(path);
        ImageIO.write(bi, ext, Files.newOutputStream(path));
    }

    /**
     * 缩放图像（按比例缩放）
     *
     * @param source 源图片
     * @param target 目标图片
     * @param scale  缩放比例
     * @param flag   缩放选择:true放大false缩小
     */
    public void scale(BufferedImage source, Path target, int scale, boolean flag) throws IOException {
        // 读入文件
        // 得到源图宽
        int width = source.getWidth();
        // 得到源图长
        int height = source.getHeight();
        // 放大
        if (flag) {
            width = width * scale;
            height = height * scale;
        } else {// 缩小
            width = width / scale;
            height = height / scale;
        }
        Image image = source.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 绘制缩小后的图
        g.drawImage(image, 0, 0, null);
        g.dispose();
        write(tag, target);
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
    public void scale(BufferedImage source, Path target, int height, int width, boolean flag) throws IOException {
        // 缩放比例
        double ratio;
        Image itemp = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // 计算比例
        if ((source.getHeight() > height) || (source.getWidth() > width)) {
            if (source.getHeight() > source.getWidth()) {
                ratio = (double) height / source.getHeight();
            } else {
                ratio = (double) width / source.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            itemp = op.filter(source, null);
        }
        // 补白
        if (flag) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == itemp.getWidth(null)) {
                g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                        itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            } else {
                g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                        itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            }
            g.dispose();
            itemp = image;
        }
        write((BufferedImage) itemp, target);
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
        ImageInputStream iis = ImageIO.createImageInputStream(Files.newInputStream(source));
        // 根据图片类型获取该种类型的ImageReader
        ImageReader reader = ImageIO.getImageReadersBySuffix(FilesUtils.getExtension(source)).next();
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceRegion(new Rectangle(x, y, width, height));
        BufferedImage bi = reader.read(0, param);
        Closee.close(iis);
        return bi;
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param source 源图片
     * @param target 目标图片
     */
    public void convert(BufferedImage source, Path target) throws IOException {
        write(source, target);
    }

    /**
     * 彩色转为黑白
     *
     * @param src    源图片
     * @param target 目标图片
     */
    public void gray(BufferedImage src, Path target) throws IOException {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        src = op.filter(Objects.requireNonNull(Optional.ofNullable(src).orElse(null)), null);
        write(src, target);
    }

    /**
     * 获取图片大小(宽x高)
     *
     * @param image 图片
     * @return {"width": 1024, "height": 768}
     */
    public Map<Integer, Integer> size(BufferedImage image) {
        // 得到源图宽
        int width = image.getWidth();
        // 得到源图长
        int height = image.getHeight();
        Map<Integer, Integer> map = new HashMap<>(2);
        map.put(WIDTH, width);
        map.put(HEIGHT, height);
        return map;
    }
}
