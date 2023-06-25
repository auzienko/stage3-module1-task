package com.mjc.school.repository.utils;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class PropertiesReader {
    private final Properties properties;

    public PropertiesReader() throws IOException {
        ClassLoader classLoader = PropertiesReader.class.getClassLoader();
        properties = new Properties();
        try (InputStream resourceAsStream = classLoader.getResourceAsStream("application.properties")) {
            properties.load(resourceAsStream);
        }
    }
}
