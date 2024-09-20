package com.geolocation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();

        try (InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("________________________________________________________________________________________________");
                System.out.println("File '" + fileName + "' was not found");
                System.out.println("________________________________________________________________________________________________");
                throw new RuntimeException("File '" + fileName + "' was not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + fileName, e);
        }

        return properties;
    }
}
