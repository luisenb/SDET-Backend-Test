package com.nasa.utils;

import com.nasa.config.Tools;
import io.restassured.RestAssured;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class BaseConfiguration {

    private static final Logger logger = LogManager.getLogger(BaseConfiguration.class.getName());

    public static void setBaseUrl(Properties properties){
        logger.log(Level.INFO, "Setting Rest Assured Base URL");
        String envVar = properties.getProperty("api.base.url");
        String envValue = Tools.resolveEnvVars(envVar);
        RestAssured.baseURI = envValue;
        logger.log(Level.INFO, "Base URL have been set");
        logger.log(Level.INFO, RestAssured.baseURI);
    }
}
