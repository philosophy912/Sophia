package com.philosophy.image.factory;

import com.philosophy.image.api.CompareEnum;
import com.philosophy.image.api.ICompare;
import com.philosophy.image.common.Hamming;
import com.philosophy.image.common.Histogram;
import com.philosophy.image.common.Pixel;

/**
 * @author lizhe
 * @date 2019/10/12:17:26
 */
public class ImageCompareFactory {

    public static ICompare createCompare(CompareEnum compareEnum) {
        switch (compareEnum) {
            case PIXEL:
                return new Pixel();
            case HISTOGRAM:
                return new Histogram();
            default:
                return new Hamming();
        }
    }
}
