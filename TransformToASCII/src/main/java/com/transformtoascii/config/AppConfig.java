package com.transformtoascii.config;

import com.transformtoascii.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
    private Properties properties;

    public void basicConfiguration() {
        properties = new Properties();

        try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                LOGGER.error("File application.properties not found");
                return;
            }

            properties.load(input);
            final String appName = this.getProperty("app.name");
            final String appVersion = this.getProperty("app.version");

            LOGGER.info("App name : {}", appName);
            LOGGER.info("App version : {}", appVersion);

        } catch (IOException ex) {
            LOGGER.error("an error has occured", ex);
        }

    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
