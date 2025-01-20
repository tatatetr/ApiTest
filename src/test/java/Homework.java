import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Homework {

    @Test
    public void FirstApi() {


        RestAssured.baseURI = "https://bookstore.toolsqa.com";

        Response responseApi = RestAssured
                .given()
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .extract().response();

        System.out.println(responseApi.getStatusCode());
        int statusCode = responseApi.getStatusCode();
        Assert.assertEquals(statusCode, 200, "CheckStatusCode");


        String result = responseApi.jsonPath().getString("books[0].author");
        String result1 = responseApi.jsonPath().getString("books[0].publisher");
        System.out.println(result);
        System.out.println(result1);
        String author = "Richard E. Silverman";
        String publisher = "O'Reilly Media";
        Assert.assertEquals(author, "Richard E. Silverman", "Check author name");
        Assert.assertEquals(publisher, "O'Reilly Media", "Check publisher name");


    }


    @Test
    public void FirstApi1() {

        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        JSONObject requestBody = new JSONObject();
        // ერთი იუზერნეიმის მეორედ გაშვებაზე მიწერს რომ უკვე რეგისტრირებულია, ამიტომ თუ მაგალითად ორჯერ გაუშვებ, გთხოვ ერთი სიმბოლო დაუმატო იუზერნეიმში რომ დარეფრეშდეს და 201 სტატუს კოდი შეინარჩუნოს
        requestBody.put("userName", "ta235ttоaa15aaTest12@");
        requestBody.put("password", "Tata4ta1fdsfs3537@");
        Response response1 = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .post("/Account/v1/User")
                .then()
                .extract().response();
        System.out.println(response1.statusCode());

        int statusCode1 = response1.getStatusCode();
        Assert.assertEquals(statusCode1, 201, "CheckStatusCode1");


        String resultOne = response1.jsonPath().getString("userID");
        System.out.println(resultOne);

        Assert.assertNotNull(response1.jsonPath().get("userID"), "Check if userID is visible in response body");


    }


    @Test
    public void postData() {

        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", "ta1ttaa15aaTest12@");
        requestBody.put("password", "fail");
        Response response2 = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .post("/Account/v1/User")
                .then()
                .extract().response();
        System.out.println(response2.statusCode());
        int statusCode2 = response2.getStatusCode();

        Assert.assertEquals(statusCode2, 400, "Check if status code is 400");
        String errorResponse = response2.jsonPath().getString("message");
        String eResponce = "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.";
        System.out.println(errorResponse);
        Assert.assertEquals(eResponce, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.", "Check error message");

    }


}
