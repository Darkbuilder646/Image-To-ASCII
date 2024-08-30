package com.transformtoascii;

import com.transformtoascii.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        LoggerConfig loggerConfig = new LoggerConfig();
        loggerConfig.basicConfiguration();

        LOGGER.info("App is starting");

        boolean isReverse = false;
        System.setProperty("console.encoding", "UTF-8");

        ImageProcessor imageProcessor = new ImageProcessor("src/main/resources/img/Saturne.jpg");
        if (imageProcessor.getImage() != null) {
            double scaleFactor = 0.25;

            BufferedImage resezedImage = imageProcessor.resizeImageWithFactor(imageProcessor.getImage(), scaleFactor);

            AsciiConverter converter = new AsciiConverter();
            converter.convertToAscii(resezedImage, 2, 4, isReverse);

        }

    }
}
