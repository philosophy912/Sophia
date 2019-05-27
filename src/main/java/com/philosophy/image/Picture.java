package com.philosophy.image;

import com.philosophy.api.IConst;
import com.philosophy.tools.Closee;
import com.philosophy.tools.FilePath;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Picture implements IConst {
    private int getLength(String text) throws UnsupportedEncodingException {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (String.valueOf(text.charAt(i)).getBytes(UTF8).length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * 读取图片文件
     *
     * @param path 文件地址
     * @return 图片的BufferedImage对象
     * @throws IOException 抛出异常
     */
    public static BufferedImage read(Path path) throws IOException {
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
        String ext = FilePath.getExtension(path);
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
        int width = source.getWidth(); // 得到源图宽
        int height = source.getHeight(); // 得到源图长
        if (flag) {// 放大
            width = width * scale;
            height = height * scale;
        } else {// 缩小
            width = width / scale;
            height = height / scale;
        }
        Image image = source.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
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
        double ratio = 0.0; // 缩放比例
        Image itemp = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // 计算比例
        if ((source.getHeight() > height) || (source.getWidth() > width)) {
            if (source.getHeight() > source.getWidth()) {
                ratio = (double) height / source.getHeight();
                // ratio = (new Integer(height)).doubleValue() / source.getHeight();
            } else {
                ratio = (double) width / source.getWidth();
                // ratio = (new Integer(width)).doubleValue() / source.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            itemp = op.filter(source, null);
        }
        if (flag) {// 补白
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == itemp.getWidth(null))
                g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            else
                g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            g.dispose();
            itemp = image;
        }
        write((BufferedImage) itemp, target);
    }

    private Graphics2D handle( Image src, int width, int height, BufferedImage image){
        Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);
        return g;
    }

    /**
     * 图片加水印文字
     *
     * @param text      水印文字目标图片
     * @param source    源图片
     * @param target    目标文件
     * @param fontName  水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color     水印的字体颜色
     * @param fontSize  水印的字体大小
     * @param x         修正值
     * @param y         修正值
     * @param alpha     透明度:alpha必须是范围[0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @throws IOException 抛出异常
     */
    public void pressText(String text, Path source, Path target, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) throws IOException {
        Image src = read(source);
        if (src != null) {
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = handle(src, width, height, image);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(text, (width - (getLength(text) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            // 输出到文件流
            write((BufferedImage) image, target);
        }
    }

    /**
     * 图片加水印图片
     *
     * @param img    水印文字
     * @param source 源图片
     * @param target 目标图片
     * @param x      修正值(默认在中间)
     * @param y      修正值(默认在中间)
     * @param alpha  透明度:alpha必须是范围[0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @throws IOException 抛出异常
     */
    public void pressText(Path img, Path source, Path target, int x, int y, float alpha) throws IOException {
        Image src = read(source);
        // 水印文件
        Image src_biao = read(img);
        if (src != null && src_biao != null) {
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = handle(src, width, height, image);

            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (width - wideth_biao) / 2 + x, (height - height_biao) / 2 + y, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            write(image, target);
        }
    }

    /**
     * 图像切割(按指定起点坐标和宽高切割)[BMP, JPG, PNG, JPEG, GIF]
     * @param source 源图片
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     * @return 切割后的图像BufferedImage对象
     */
    public BufferedImage cut(Path source, int x, int y, int width, int height) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(Files.newInputStream(source));
        // 根据图片类型获取该种类型的ImageReader
        ImageReader reader = ImageIO.getImageReadersBySuffix(FilePath.getExtension(source)).next();
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceRegion(new Rectangle(x, y, width, height));
        BufferedImage bi = reader.read(0, param);
        Closee.close(iis);
        return bi;
    }

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param source 源图片
     * @param target 目标图片
     */
    public void convert(Path source, Path target) throws IOException {
        BufferedImage src = read(source);
        write(Optional.ofNullable(src).get(), target);
    }

    /**
     *彩色转为黑白
     * @param source 源图片
     * @param target 目标图片
     */
    public void gray(Path source, Path target) throws IOException {
        BufferedImage src= read(source);
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        src = op.filter(Optional.ofNullable(src).get(), null);
        write(src, target);
    }

    /**
     * 获取图片大小(宽x高)
     * @param image 图片
     * @return 宽高数组[Width, Height]
     */
    public int[] size(BufferedImage image){
        int width = image.getWidth(); // 得到源图宽
        int height = image.getHeight(); // 得到源图长
        return new int[]{width, height};
    }
}
