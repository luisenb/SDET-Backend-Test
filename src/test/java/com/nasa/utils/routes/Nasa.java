package com.nasa.utils.routes;

import java.util.Properties;

public class Nasa {

    private String apiVersion;
    private String itemLocation;

    public Nasa(Properties properties, String itemLocation){
        apiVersion = properties.getProperty("api.base.version");
        this.itemLocation = itemLocation;
    }

    public String getFullPath(String nasaOption) {
        return itemLocation + apiVersion + nasaOption;
    }
}
