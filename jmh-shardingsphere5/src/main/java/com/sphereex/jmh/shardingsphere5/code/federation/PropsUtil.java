package com.sphereex.jmh.shardingsphere5.code.federation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropsUtil {

    public static Properties loadProperties(String configPath, String configFileName) {
        return loadProperties(configPath + configFileName);
    }
    
    public static Properties loadProperties(String configFile) {
        Properties result = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get(configFile))) {
            result.load(inputStream);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}
