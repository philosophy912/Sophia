package com.philosophy.image;

import com.philosophy.api.image.ICompare;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class Histogram implements ICompare {
    private float getBinIndex(int binCount, int color, int colorMaxValue) {
        float binIndex = (((float) color) / ((float) colorMaxValue)) * ((float) binCount);
        if (binIndex >= binCount)
            binIndex = binCount - 1;
        return binIndex;
    }

    private int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
        }
        return image.getRGB(x, y, width, height, pixels, 0, width);

    }

    private float[] filter(BufferedImage src, Object dest) {
        int redBins = 4;
        int greenBins = 4;
        int blueBins = 4;
        int width = src.getWidth();
        int height = src.getHeight();
        int[] inPixels = new int[width * height];
        float[] histogramData = new float[redBins * greenBins * blueBins];
        getRGB(src, 0, 0, width, height, inPixels);
        int index = 0;
        int redIdx = 0, greenIdx = 0, blueIdx = 0;
        int singleIndex = 0;
        float total = 0;
        for (int row = 0; row < height; row++) {
            int tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;
                redIdx = (int) getBinIndex(redBins, tr, 255);
                greenIdx = (int) getBinIndex(greenBins, tg, 255);
                blueIdx = (int) getBinIndex(blueBins, tb, 255);
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
        float[] sourceData = filter(image1, null);
        float[] candidateData = filter(image2, null);
        double[] mixedData = new double[sourceData.length];
        for (int i = 0; i < sourceData.length; i++) {
            mixedData[i] = Math.sqrt(sourceData[i] * candidateData[i]);
        }
        // The values of Bhattacharyya Coefficient ranges from 0 to 1,
        for (int i = 0; i < mixedData.length; i++) {
            similarity += mixedData[i];
        }
        // The degree of similarity
        return similarity;
    }

    @Override
    public double compare(Path image1, Path image2) throws IOException {
        return histogram(Picture.read(image1), Picture.read(image2));
    }
}
