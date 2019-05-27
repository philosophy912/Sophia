package com.philosophy.api.image;

import java.io.IOException;
import java.nio.file.Path;

public interface ICompare {
    /**
     * 比较两张图片
     * @param image1 图片1
     * @param image2 图片2
     * @return 比较的百分比值
     */
    double compare(Path image1, Path image2) throws IOException;
}
