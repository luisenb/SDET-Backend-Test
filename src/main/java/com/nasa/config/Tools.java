package com.nasa.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    private static final Logger logger = LogManager.getLogger(Tools.class.getName());

    public static <T> String convertJsonToString(T json){
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAsString = null;
        try{
            jsonAsString = objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return jsonAsString;
    }

    public static <T> T convertStringToJson(String json,
                                            Class<T> clazz)
            throws JsonProcessingException, ClassNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        Class<T> model = (Class<T>) Class.forName(clazz.getName());
        T object = mapper.readValue(json, model);
        return object;
    }

    public static <T> List<T> convertStringToJsonArray(String json,
                                                       Class<T> clazz)
            throws ClassNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + clazz+";");
        T[] objects = mapper.readValue(json, arrayClass);
        return Arrays.asList(objects);
    }

    public static String resolveEnvVars(String input)
    {
        if (null == input)
        {
            return null;
        }
        // match ${ENV_VAR_NAME} or $ENV_VAR_NAME
        Pattern p = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
        Matcher m = p.matcher(input); // get a matcher object
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            String envVarName = null == m.group(1) ? m.group(2) : m.group(1);
            String envVarValue = System.getenv(envVarName);
            m.appendReplacement(sb, null == envVarValue ? "" : envVarValue);
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
