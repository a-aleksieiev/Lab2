import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class userTest {

    private static final String baseUrl = "https://restful-booker.herokuapp.com";

    private static final String
    USER_AUTH = "auth",
    USER_BOOKING = "/booking/";

    public int bookingId;
    private String token;

    @BeforeClass
    public void setup()
    {
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test(priority = 1)
    public void userAuth()
    {
        String requestBody = "{ \"username\" : \"admin\", \"password\" : \"password123\" }";

        // Send the POST request and capture the response
        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .body(requestBody)
                .post(USER_AUTH);
        System.out.println("Test №1:");
        // Print the response body
        System.out.println("Response Body: " + response.getBody().asString());
        token = response.then().extract().path("token");
        response.then().statusCode(200);
        System.out.println("Status code: " + response.getStatusCode());
    }

    @Test(priority = 2)
    public void verifyBooking()
    {
        String requestBody = "{ " +
                "\"firstname\" : \"Oleksii\"," +
                "\"lastname\" : \"Aleksieiev\"," +
                "\"totalprice\" : 111," +
                "\"depositpaid\" : true," +
                "\"bookingdates\" : {" +
                "\"checkin\" : \"2018-01-01\"," +
                "\"checkout\" : \"2019-01-01\"" +
                "}," +
                "\"additionalneeds\" : \"Breakfast\"" +
                "}";

        // Send the POST request and capture the response
        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .body(requestBody)
                .post(USER_BOOKING);

        bookingId = response.then().extract().path("bookingid");
        System.out.println("Test №2:");
        // Print the response body
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status code: " + response.getStatusCode());

        response.then().statusCode(200);
    }
    @Test(priority = 3)
    public void getBookingDetails() {

        String endpoint = USER_BOOKING + bookingId;

        // Send the GET request and capture the response
        Response response = RestAssured.given()
                .get(endpoint);
        System.out.println("Test №3:");
        // Print the response body
        System.out.println("Booking Details Response Body: " + response.getBody().asString());
        System.out.println("Status code: " + response.getStatusCode());

        response.then().statusCode(200);
    }

    @Test(priority = 4)
    public void putUser()
    {
        String endpoint = USER_BOOKING + bookingId;
        String requestBody = "{ " +
                "\"firstname\" : \"Oleksii Aleksieiev\"," +
                "\"lastname\" : \"122M-23-1\"," +
                "\"totalprice\" : 111," +
                "\"depositpaid\" : true," +
                "\"bookingdates\" : {" +
                "\"checkin\" : \"2018-01-01\"," +
                "\"checkout\" : \"2019-01-01\"" +
                "}," +
                "\"additionalneeds\" : \"Breakfast\"" +
                "}";

        Response response = RestAssured.given()
                .header("Content-Type", ContentType.JSON)
                .cookie("token", token) // Send the token as a cookie
                .body(requestBody)
                .put(endpoint);
        System.out.println("Test №4:");
        System.out.println("Update Booking Response Body: " + response.getBody().asString());
        System.out.println("Status code: " + response.getStatusCode());
        response.then().statusCode(200);
    }
}
