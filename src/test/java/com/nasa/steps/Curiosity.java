package com.nasa.steps;

import com.nasa.base.StepBase;
import com.nasa.utils.routes.Nasa;
import com.nasa.utils.routes.NasaOptions;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Properties;

public class Curiosity extends StepBase {

    public Curiosity(Properties properties, String itemLocation){
        super(properties, itemLocation);
    }

    public Response getRoversPhotos(Map<String, String> formParams){
        String curiosityRoversPath =
                NasaOptions.CURIOSITY.getRoversPath();
        return get(formParams, curiosityRoversPath);
    }
    public Response getManifest(){
        String curiosityRoversPath =
                NasaOptions.CURIOSITY.getManifestPath();
        return get(curiosityRoversPath);
    }


}
