package Rest_assured_intro;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Post_Request {



    //for post function
    String jsonRequest = "{\n" +
            "    \"RuleApp\": {\n" +
            "        \"RepositoryRuleAppRevisionSpec\": {\n" +
            "            \"RuleApplicationName\": \"CoreSystemCoverage\"\n" +
            "        },\n" +
            "        \"RepositoryServiceUri\": \"https://trial-asjquy-244-catalog.trial.inrule.com/service.svc\",\n" +
            "        \"UserName\": \"crm_user\",\n" +
            "        \"Password\": \"Welcome@1\"\n" +
            "    },\n" +
            "    \"RuleEngineServiceOutputTypes\": {\n" +
            "        \"ActiveNotifications\": true,\n" +
            "        \"ActiveValidations\": true,\n" +
            "        \"EntityState\": true,\n" +
            "        \"Overrides\": false,\n" +
            "        \"RuleExecutionLog\": false\n" +
            "    },\n" +
            "    \"EntityName\": \"AddInsuredRequest\",\n" +
            "    \"EntityState\": \"{\\\"Facility\\\":{\\\"BusinessStartDate\\\":\\\"2015-2-26T00:00:00\\\",\\\"SectorCode\\\":3},\\\"InsuredPersonSIN\\\":{\\\"Birthdate\\\":\\\"1993-02-09T00:00:00\\\",\\\"SINGender\\\":2},\\\"PeriodTypeCode\\\":\\\"01\\\",\\\"SectorCode\\\":3,\\\"SubscriptionCode\\\":\\\"11\\\",\\\"SubscriptionRequestDate\\\":\\\"2023-08-01T00:00:00\\\",\\\"SubscriptionStartDate\\\":\\\"2023-01-01T00:00:00\\\",\\\"AddInsuredWageDetails\\\":[{\\\"WageStartDate\\\":\\\"2023-01-01T00:00:00\\\",\\\"WageValue\\\":1700,\\\"WageType\\\": \\\"2\\\"},{\\\"WageStartDate\\\": \\\"2023-01-1T00:00:00\\\",\\\"WageValue\\\": 1800,\\\"WageType\\\": \\\"4\\\"}],\\\"IsSubscribedInAdditionalPesnsion\\\":false}\"\n" +
            "}";

    @Test
    public void POST_REQUEST(){
        // Set base URI
        RestAssured.baseURI = "http://192.168.12.99";

        // Send POST request with JSON content
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "APIKEY 2d04b070a8354fbea0e8316798ae384a")
                .body(jsonRequest)
                .post("/InRuleRuleEngineService/HttpService.svc/ApplyRules");


        // Assert on specific value in the response

        response.then()
                .assertThat().statusCode(200).contentType(ContentType.XML);

        // Extract the JSON string from the XML response
        String jsonString = response.getBody().xmlPath().getString("RuleEngineHttpServiceResponse.EntityState");
        // Parse the JSON string
        JsonPath jsonPath = new JsonPath(jsonString);
        // Retrieve the value of "CurrentYearSubscription"&"PrevYearSubscription" from the parsed JSON
        String currentYearSubscription = jsonPath.getString("Response.AccountSummary.CurrentYearSubscription");
        String PrevYearSubscription = jsonPath.getString("Response.AccountSummary.PrevYearSubscription");

        // assert on the value of "CurrentYearSubscription" & "PrevYearSubscription"
        Assert.assertEquals("505.75", currentYearSubscription);
        Assert.assertEquals("3034.5",PrevYearSubscription);


        // Print response details
        System.out.println("Response status code: " + response.getStatusCode() );
        System.out.println("Response body:");
        System.out.println(response.getBody().asString());
    }


}
