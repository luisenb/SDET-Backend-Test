package com.nasa.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nasa.config.Tools;
import com.nasa.pojo.manifests.PhotosItem;
import com.nasa.pojo.manifests.ResponseManifest;
import com.nasa.pojo.rovers.ResponseRover;
import com.nasa.utils.config.CustomisedReport;
import com.nasa.utils.routes.Nasa;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class StepBase {

    protected RequestSpecification request;
    protected Properties properties;
    protected String itemLocation;

    private static final Logger logger = LogManager.getLogger(StepBase.class.getName());

    public StepBase(Properties properties, String itemLocation){
        this.properties = properties;
        this.itemLocation = itemLocation;
        String envVar = properties.getProperty("api.base.key");
        String envValue = Tools.resolveEnvVars(envVar);
        String api_key = envValue;
        request = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .param("api_key", api_key);
    }

    protected Response get(Map<String, String> formParams, String nasaOption){
        String url = new Nasa(properties, itemLocation)
                .getFullPath(nasaOption);
        Response response = request
                .formParams(formParams)
                .log().all()
                .get(url);
        return response;
    }
    protected Response get(String nasaOption){
        String url = new Nasa(properties, itemLocation)
                .getFullPath(nasaOption);
        Response response = request
                .log().all()
                .get(url);
        return response;
    }

    public  <T> T getBody(Response response, Class<T> clazz){
        String jsonBody = response.getBody().asString();
        T object = null;
        try {
            object = Tools.convertStringToJson(jsonBody, clazz);
        } catch (JsonProcessingException | ClassNotFoundException e) {
            logger.log(Level.TRACE, "The json mapping has failed.");
            logger.error(e.getMessage());
            System.exit(0);
        }
        return object;
    }

    public List<String> getList(int number, ResponseRover responseRover){
        List<String> finalList = responseRover
                .getPhotos()
                .stream()
                .limit(number)
                .map(photosItem -> photosItem.getImgSrc())
                .collect(Collectors.toList());
        return finalList;
    }

    public int getCount(String date, ResponseManifest responseManifest){
        Optional optional = responseManifest
                .getPhotoManifest()
                .getPhotos()
                .stream()
                .filter(photosItem -> photosItem.getEarthDate().equals(date))
                .findFirst();
        int total = 0;
        if(optional.isPresent()){
            PhotosItem photosItem = (PhotosItem) optional.get();
            total = photosItem.getTotalPhotos();
        }
        return total;
    }
    public int getCount(String date, int sol, ResponseManifest responseManifest){
        Optional optional = responseManifest
                .getPhotoManifest()
                .getPhotos()
                .stream()
                .filter(photosItem -> photosItem.getEarthDate().equals(date) && photosItem.getSol() == sol)
                .findFirst();
        int total = 0;
        if(optional.isPresent()){
            PhotosItem photosItem = (PhotosItem) optional.get();
            total = photosItem.getTotalPhotos();
        }
        return total;
    }

}
