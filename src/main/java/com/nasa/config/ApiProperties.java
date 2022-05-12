package com.nasa.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiProperties {

    private static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final Logger logger = LogManager.getLogger(ApiProperties.class.getName());
    private static String apiConfigPath = rootPath + "api.properties";

    public static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(apiConfigPath));
        logger.log(Level.INFO, "Api Properties have been configured.");
        return properties;
    }
}
