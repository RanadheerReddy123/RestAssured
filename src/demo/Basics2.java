package demo;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import files.Payload;
public class Basics2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
RestAssured.baseURI = "https://rahulshettyacademy.com";
String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body(Payload.AddPlace())
.when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
System.out.println(response);
	}

}
