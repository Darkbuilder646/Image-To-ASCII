package com.transformtoascii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class AsciiConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsciiConverter.class);

    public void convertToAscii(BufferedImage image, int cellWidth, int cellHeight, boolean reverseGray) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        for (int y = 0; y < imageHeight; y += cellHeight) {
            for (int x = 0; x < imageWidth; x += cellWidth) {
                if (x + cellWidth <= imageWidth && y + cellHeight <= imageHeight) {
                    int gray = getAverageGrayValue(image, x, y, cellWidth, cellHeight);

                    char brailleChar = getAsciiChar(gray, reverseGray);
                    System.out.print(brailleChar);
                }
            }
            System.out.println();
        }
    }

    private int getAverageGrayValue(BufferedImage image, int startX, int startY, int width, int height) {
        int sum = 0;
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                int rgb = image.getRGB(x, y);
                int gray = convertPixelToGray(rgb);
                sum += gray;
            }
        }
        return sum / (width * height);
    }

    private int convertPixelToGray(int rgb) {
        return (int) (0.21 * ((rgb >> 16) & 0xFF) + 0.72 * ((rgb >> 8) & 0xFF) + 0.07 * (rgb & 0xFF));
    }

    private char getAsciiChar(int gray, boolean reverseGray) {
        String listChars = reverseGray ? "@%#*+=-:. " : " .:-=+*#%@" ;

        int index = gray * (listChars.length() - 1) / 255;

        return listChars.charAt(index);
    }
}
