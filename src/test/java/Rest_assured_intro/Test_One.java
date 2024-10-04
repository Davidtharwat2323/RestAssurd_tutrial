package Rest_assured_intro;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Test_One {

/*
    @BeforeTest
    public void Setup() {
        //WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        //ChromeDriverService service = new ChromeDriverService.Builder().usingPort(1234).build();
        driver.manage().window().maximize();
        driver.get("http://192.168.12.99/InRuleRuleEngineService/HttpService.svc/ApplyRules");
    }

*/
    //for get function
    String RequestURI = "http://zippopotam.us";
    String body = "http://zippopotam.us/us/90210";


    String all = RequestURI + body;



    @Test
    public void get_request() {

        given().
                log().all().
                baseUri(RequestURI).basePath(body).
                // port(10433).
                        when().get(all).
                then()
                .assertThat().statusCode(200).
                log().body();

    }



























    //-----------------------------------Assertions Samples---------------------------------------------//

    @Test
    //Status code 200
    public void requestUsZipCode90210_logRequestAndResponseDetails() {
String Request="http://zippopotam.us/us/90210";
        given().
                log().all().
                when().
                get(Request).
                then(). assertThat().statusCode(200).log().body();
    }
    @Test
    //check a value in long response
    public void requestUsZipCode90210_checkListOfPlaceNamesInResponseBody_expectContainsBeverlyHills() {

        given().
                log().all().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places.'place name'", hasItem("Beverly Hills")).log().body();


    }

    @Test
    //check number of retrieved values in response
    public void requestUsZipCode90210_checkNumberOfPlaceNamesInResponseBody_expectOne() {

        given().
                log().all().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places.'place name'", hasSize(1)).log().body();
    }

    @Test
    //check specific value in response
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                log().all().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                statusCode(200).
                body("places[0].'place name'", equalTo("Beverly Hills")).log().body();

    }

    @Test
    //check Response in type JSON/XML
    public void requestUsZipCode90210_checkContentType_expectApplicationJson() {

        given().
                log().all().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                contentType(ContentType.JSON).log().body();
    }

}
