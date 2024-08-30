package com.transformtoascii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        Properties properties = new Properties();

        try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                LOGGER.error("File application.properties not found");
                return;
            }

            properties.load(input);
            final String appName = properties.getProperty("app.name");
            final String appVersion = properties.getProperty("app.version");

            LOGGER.info("App name : {}", appName);
            LOGGER.info("App version : {}", appVersion);


        } catch (IOException ex) {
            LOGGER.error("an error has occured", ex);
        }

        LOGGER.debug("Logger configured by application.properties");
        LOGGER.debug("Starting app");
        LOGGER.warn("Waring message 1");
        LOGGER.debug("hello world 1");
        LOGGER.debug("hello world 2");
        LOGGER.info("hello world 3");
        LOGGER.warn("Waring message 2");
        LOGGER.error("Error message here");

    }
}
