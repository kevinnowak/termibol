package com.gitlab.kevinnowak;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Configuration {

    private final Properties properties = new Properties();

    Configuration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Properties file not found");
        }
    }

    String getProperty(String key) {
        return properties.getProperty(key);
    }
}
