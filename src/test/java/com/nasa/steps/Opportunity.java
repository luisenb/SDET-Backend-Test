package com.nasa.steps;

import com.nasa.base.StepBase;
import com.nasa.utils.routes.NasaOptions;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Properties;

public class Opportunity extends StepBase {

    public Opportunity(Properties properties, String itemLocation){
        super(properties, itemLocation);
    }

    public Response getRoversPhotos(Map<String, String> formParams){
        String curiosityRoversPath =
                NasaOptions.OPPORTUNITY.getRoversPath();
        return get(formParams, curiosityRoversPath);
    }
    public Response getManifest(){
        String curiosityRoversPath =
                NasaOptions.OPPORTUNITY.getManifestPath();
        return get(curiosityRoversPath);
    }
}
