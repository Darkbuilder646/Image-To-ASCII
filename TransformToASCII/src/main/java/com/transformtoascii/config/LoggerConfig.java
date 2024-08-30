package com.transformtoascii.config;

import com.transformtoascii.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoggerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConfig.class);

    public void basicConfiguration() {
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
    }

}
