package com.transformtoascii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessor.class);
    private final BufferedImage image;

    public ImageProcessor(String filePath) {
        this.image = loadImage(filePath);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    private BufferedImage loadImage(String filePath) {
        File file = new File(filePath);
        try {
            if (file.exists()) {
                LOGGER.info("File loading");
                return ImageIO.read(file);
            } else {
                LOGGER.warn("File not found");
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("Error while loading image", e);
            return null;
        }
    }

    public BufferedImage resizeImageWithFactor(BufferedImage image, double factor) {
        int newWidth = (int) (image.getWidth() * factor);
        int newHeight = (int) (image.getHeight() * factor);

        LOGGER.info("New dimension of image : {}w {}h", newWidth, newHeight);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resizedImage;
    }

}
