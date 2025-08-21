package com.joe.asciirenderer;

import java.awt.image.BufferedImage;

public class AsciiRenderer {
    public static String toAscii(BufferedImage img, int kernelSize) {
        int[][] pixelArray = getPixelArray(img);
        StringBuilder asciiOutput = new StringBuilder();
        String density = "Ñ@#W$9876543210?!abc;:+=-,._ ";

        for (int row = 0; row < pixelArray.length; row += kernelSize) {
            for (int col = 0; col < pixelArray[row].length; col += kernelSize) {
                int count = 0;
                int total = 0;

                for (int row_off = 0; row_off < kernelSize; row_off++) {
                    if (row + row_off >= pixelArray.length) continue;
                    for (int col_off = 0; col_off < kernelSize; col_off++) {
                        if (col + col_off >= pixelArray[row].length) continue;

                        count++;
                        int rgb = pixelArray[row + row_off][col + col_off];
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = (rgb) & 0xFF;
                        total += (r + g + b) / 3;
                    }
                }

                int avg = total / count;
                int indexAscii = (density.length() - 1) - (avg * (density.length() - 1) / 255);
                asciiOutput.append(density.charAt(indexAscii));
            }
            asciiOutput.append("\n");
        }
        return asciiOutput.toString();
    }

    private static int[][] getPixelArray(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = img.getRGB(col, row);
            }
        }
        return result;
    }
}
