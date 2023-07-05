package demo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReUsableMethods;

public class Basics {

	public static void main(String[] args) {
		// validate if AddPlace API is working as expected
		//given - all input details
		//when - Submit the API, resource and http methods we have to enter here
		//then - validate the response
		//		RestAssured.baseURI="https://rahulshettyacademy.com";
		//		given().log().all().queryParam("Key", "qaclick123").header("Content-Type","application/json").body(Payload.AddPlace())
		//		.when().post("/maps/api/place/add/json")
		//		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		//		.header("server", "Apache/2.4.41 (Ubuntu)");

		//Add place --> Update place with new address --> Get place to validate if new address is present in response

		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("Key", "qaclick123").header("Content-Type","application/json").body(Payload.AddPlace())
				.when().post("/maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		System.out.println(response);
		JsonPath js=new JsonPath(response); //adding response value to some path to store it
		String placeId=js.getString("place_id");//here place_id is one of the variable in response file

		System.out.println("The address of the response json at present is: "+placeId);

		//Update Place
		String newAddress = "Summer Walk, Africa";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").
		when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

		//Get Place

		String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id",placeId)
				.when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();
		JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse);//ReUsableMethods is class in files package and rawToJson is method in it
		String actualAddress =js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		//Cucumber Junit, Testng

	}

}
