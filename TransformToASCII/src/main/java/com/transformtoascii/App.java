package com.transformtoascii;

import com.transformtoascii.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        //Basic config
        AppConfig appConfig = new AppConfig();
        appConfig.basicConfiguration();
        boolean isReverse = Boolean.parseBoolean(appConfig.getProperty("app.image.reverseGrayscale"));
        boolean graphiqueInterface = Boolean.parseBoolean(appConfig.getProperty("app.useInterface"));

        LOGGER.info("App is starting...");

        if (graphiqueInterface) {
            InterfaceSwing interfaceSwing = new InterfaceSwing();
            interfaceSwing.showInterface();

        } else {
            System.setProperty("console.encoding", "UTF-8");

            ImageProcessor imageProcessor = new ImageProcessor(appConfig.getProperty("app.image.default"));
            if (imageProcessor.getImage() != null) {
                double scaleFactor = 0.25;

                BufferedImage resezedImage = imageProcessor.resizeImageWithFactor(imageProcessor.getImage(), scaleFactor);

                AsciiConverter converter = new AsciiConverter();
                converter.convertToAscii(resezedImage, 2, 4, isReverse);

            }
        }

    }
}
