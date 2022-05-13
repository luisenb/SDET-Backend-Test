package com.nasa.test;

import com.nasa.base.TestBase;
import com.nasa.config.Tools;
import com.nasa.pojo.manifests.ResponseManifest;
import com.nasa.pojo.rovers.ResponseRover;
import com.nasa.steps.Curiosity;
import com.nasa.steps.Opportunity;
import com.nasa.steps.Spirit;
import com.nasa.utils.config.CustomisedReport;
import com.nasa.utils.config.Params;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Listeners({CustomisedReport.class})
public class CuriosityTest extends TestBase {

    @Test(groups = "Regression", testName = "Get the first 10 Mars Photos by Curiosity",description = "Retrieve the first 10 Mars photos made by \"Curiosity\" on 1000 Martian sol.")
    public void curiosityMartianSol(){

        // Adding any params needed
        Map<String, String> formParams = new HashMap<>();
        formParams.put(Params.SOL.getName(), "1000");

        // Adding the request to the report
        String request = Tools.convertJsonToString(formParams.entrySet());
        addAttributeToReport("Request", request);

        //Getting the photos by curiosity
        Curiosity curiosity = new Curiosity(properties, "/mars-photos");
        Response response = curiosity.getRoversPhotos(formParams);
        ResponseRover responseRover = curiosity.getBody(response, ResponseRover.class);
        addJsonToReport("Response", responseRover);
        //Getting only the first 10 images (1000 Sol)
        List<String> list = curiosity.getList(10, responseRover);
        int size = list.size();
        Assert.assertTrue(size == 10);
    }

    @Test(groups = "Regression", testName = "Get the first 10 Mars photos by Curiosity by Earth Date",description = "etrieve the first 10 Mars photos made by \"Curiosity\" on Earth date equal to 1000 Martian sol.")
    public void curiosityEarthDate(){

        // Adding any params needed
        Map<String, String> formParams = new HashMap<>();
        formParams.put(Params.EARTH_DATE.getName(), "2015-5-3");

        // Adding the request to the report
        String request = Tools.convertJsonToString(formParams.entrySet());
        addAttributeToReport("Request", request);

        //Getting the photos by curiosity
        Curiosity curiosity = new Curiosity(properties, "/mars-photos");
        Response responseEarthDate = curiosity.getRoversPhotos(formParams);
        ResponseRover responseRover = curiosity.getBody(responseEarthDate, ResponseRover.class);
        addJsonToReport("Response", responseRover);
        //Getting only the first 10 images (Earth date)
        List<String> list = curiosity.getList(10, responseRover);
        int size = list.size();
        Assert.assertTrue(size == 10, "There is more or less than 10 photos");
    }

    @Test(groups = "Regression", testName = "Comparing the first 10 photos from sol with earth date",description = "Retrieve and compare the first 10 Mars photos made by \"Curiosity\" on 1000 sol and on Earth date equal to 1000 Martian sol.")
    public void curiosityCompare(){

        // Adding any params needed
        Map<String, String> formParams = new HashMap<>();
        formParams.put(Params.SOL.getName(), "1000");

        // Adding the request to the report
        String request = Tools.convertJsonToString(formParams.entrySet());

        //Getting the photos by curiosity
        Curiosity curiosity = new Curiosity(properties, "/mars-photos");
        Response responseSol = curiosity.getRoversPhotos(formParams);
        ResponseRover responseMartian = curiosity.getBody(responseSol, ResponseRover.class);

        //Clear params to add a new instance
        formParams.clear();
        formParams.put(Params.EARTH_DATE.getName(), "2015-5-3");
        String requestEarthDate = Tools.convertJsonToString(formParams.entrySet());
        addAttributeToReport("Request", request + " \n " + requestEarthDate);

        //Get the photos by earth date
        Response responseEarthDate = curiosity.getRoversPhotos(formParams);
        ResponseRover responseEarth = curiosity.getBody(responseEarthDate, ResponseRover.class);
        // Get only 10 photos of each one
        List<String> photoListSol = curiosity.getList(10, responseEarth);
        List<String> photoListEarth = curiosity.getList(10, responseMartian);

        addJsonToReport("Response", photoListEarth + " \n " + photoListSol);

        Assert.assertEquals(photoListEarth, photoListSol, "Martian Sol Photos are not equal to Earth Date photos");

    }

    @Test(groups = "Regression", testName = "Comparing Opportunity Curiosity and Spirit", description = "Validate that the amounts of pictures that each \"Curiosity\" camera took on 1000 Mars sol is not greater than 10 times the amount taken by other cameras on the same date.")
    public void curiosityComparison(){

        int sol = 1000;
        String date = "2015-05-30";

        String itemLocation = "/mars-photos";

        // Get curiosity photos
        Curiosity curiosity = new Curiosity(properties, itemLocation);
        Response response = curiosity.getManifest();
        ResponseManifest responseManifestCuriosity =
                curiosity.getBody(response, ResponseManifest.class);

        // Get Spirit photos
        Spirit spirit = new Spirit(properties, itemLocation);
        Response responseSpirit = spirit.getManifest();
        ResponseManifest responseManifestSpirit =
                spirit.getBody(responseSpirit, ResponseManifest.class);

        // Get Opportunity photos
        Opportunity opportunity = new Opportunity(properties, itemLocation);
        Response responseOpportunity = opportunity.getManifest();
        ResponseManifest responseManifestOpportunity =
                opportunity.getBody(responseOpportunity, ResponseManifest.class);

        addAttributeToReport("Request", "Sol: " + sol + "\n Date: " + date);

        int totalPhotosCuriosity = curiosity.getCount(date, sol, responseManifestCuriosity);
        int totalPhotosSpirit = spirit.getCount(date, responseManifestSpirit);
        int totalPhotosOpportunity = opportunity.getCount(date, responseManifestOpportunity);
        int totalPhotosOtherCamaras = (totalPhotosSpirit + totalPhotosOpportunity) * 10;

        addAttributeToReport("Response", "Curiosity Photos: " + totalPhotosCuriosity
                + "\n Spirit and Opportunity Photos: " + totalPhotosOtherCamaras);
        Assert.assertTrue(totalPhotosCuriosity < totalPhotosOtherCamaras,
                "The amount of pictures taken by Curiosity("+ totalPhotosCuriosity+") is greater than 10 times "
                        + "the amount of pictures taken by other camaras(" + totalPhotosOtherCamaras + ")");

    }
}
