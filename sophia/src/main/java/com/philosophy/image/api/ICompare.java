package com.philosophy.image.api;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author lizhe
 */
public interface ICompare {

    /**
     * 比较两张图片
     *
     * @param image1 图片1
     * @param image2 图片2
     * @return 比较的百分比值
     * @throws IOException
     */
    double compare(BufferedImage image1, BufferedImage image2);
}
