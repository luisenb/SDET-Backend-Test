package com.nasa.utils.routes;

public enum NasaOptions {
    CURIOSITY,
    SPIRIT,
    OPPORTUNITY;

    private String getPath(String path, String name){
        String completePath = String.format(path, name);
        return completePath;
    }

    public String getManifestPath(){
        String name = this.toString();
        String path = getPath("/manifests/%s", name);
        return path;
    }

    public String getRoversPath(){
        String name = this.toString();
        String path = getPath("/rovers/%s/photos", name);
        return path;
    }
}
