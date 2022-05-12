package com.nasa.utils.config;

public enum Params {
    SOL,
    EARTH_DATE;

    public String getName(){
        return this.toString().toLowerCase();
    }
}
