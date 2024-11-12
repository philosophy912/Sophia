package com.philosophy.image.common;


import com.philosophy.image.api.ICompare;

import java.awt.image.BufferedImage;

/**
 * @author lizhe
 */
public class Histogram implements ICompare {
    private float getBinIndex(int binCount, int color) {
        float binIndex = (((float) color) / ((float) 255)) * ((float) binCount);
        if (binIndex >= binCount) {
            binIndex = binCount - 1;
        }
        return binIndex;
    }

    private void getRgb(BufferedImage image, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            image.getRaster().getDataElements(0, 0, width, height, pixels);
            return;
        }
        image.getRGB(0, 0, width, height, pixels, 0, width);

    }

    private float[] filter(BufferedImage src) {
        int redBins = 4;
        int greenBins = 4;
        int blueBins = 4;
        int width = src.getWidth();
        int height = src.getHeight();
        int[] inPixels = new int[width * height];
        float[] histogramData = new float[redBins * greenBins * blueBins];
        getRgb(src, width, height, inPixels);
        int index;
        int redIdx, greenIdx, blueIdx;
        int singleIndex;
        float total = 0;
        for (int row = 0; row < height; row++) {
            int tr, tg, tb;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;
                redIdx = (int) getBinIndex(redBins, tr);
                greenIdx = (int) getBinIndex(greenBins, tg);
                blueIdx = (int) getBinIndex(blueBins, tb);
                singleIndex = redIdx + greenIdx * redBins + blueIdx * redBins * greenBins;
                histogramData[singleIndex] += 1;
                total += 1;
            }
        }

        // start to normalize the histogram data
        for (int i = 0; i < histogramData.length; i++) {
            histogramData[i] = histogramData[i] / total;
        }

        return histogramData;
    }

    private double histogram(BufferedImage image1, BufferedImage image2) {
        double similarity = 0;
        float[] sourceData = filter(image1);
        float[] candidateData = filter(image2);
        double[] mixedData = new double[sourceData.length];
        for (int i = 0; i < sourceData.length; i++) {
            mixedData[i] = Math.sqrt(sourceData[i] * candidateData[i]);
        }
        // The values of Bhattacharyya Coefficient ranges from 0 to 1,
        for (double mixedDatum : mixedData) {
            similarity += mixedDatum;
        }
        // The degree of similarity
        return similarity;
    }

    @Override
    public double compare(BufferedImage image1, BufferedImage image2) {
        return histogram(image1, image2);
    }
}
