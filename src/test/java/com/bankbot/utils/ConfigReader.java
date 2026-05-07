package com.bankbot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPERTIES = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException(CONFIG_FILE + " was not found in src/test/resources.");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load " + CONFIG_FILE, e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        return get(key, "");
    }

    public static String get(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        String value = get(key, String.valueOf(defaultValue));
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Configuration value for " + key + " must be a number: " + value, e);
        }
    }
}
