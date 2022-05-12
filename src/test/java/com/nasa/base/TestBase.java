package com.nasa.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.config.ApiProperties;
import com.nasa.utils.BaseConfiguration;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestBase {

    private static final Logger logger = LogManager.getLogger(TestBase.class.getName());

    public static Properties properties;

    @BeforeClass(alwaysRun = true)
    public void init(){
        try {
            properties = ApiProperties.getProperties();
            logger.log(Level.INFO, "Properties have been configured");
            BaseConfiguration.setBaseUrl(properties);
        }catch (IOException e) {
            logger.log(Level.TRACE, "You need to set the api.properties");
            logger.log(Level.ERROR, e.getMessage());
            System.exit(0);
        }
    }

    public void addAttributeToReport(String key, String value){
        ITestResult iTestResult = Reporter.getCurrentTestResult();
        iTestResult.setAttribute(key, value);
    }

    public <T> void addJsonToReport(String key, T value){
        ObjectMapper mapper = new ObjectMapper();
        String stringValue = null;
        try {
            stringValue = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        }catch (JsonProcessingException e) {
            logger.log(Level.ERROR, "The following key could not be mapped: " + key);
            logger.log(Level.ERROR, e.getMessage());
        }
        ITestResult iTestResult = Reporter.getCurrentTestResult();
        iTestResult.setAttribute(key, stringValue);
    }

}
