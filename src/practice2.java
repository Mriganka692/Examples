import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import files.newplace;

public class practice2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String resource = "maps/api/place/add/json";
		
		//Post
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(newplace.PlaceAdd())
		.when().post(resource)
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String placeid=js.getString("place_id");
		System.out.println("place id" + " " + placeid);
		
		//update
String newaddress = "first walk,India";
		
		String response1=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\""+newaddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"))
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response1);
		
		JsonPath js1 = new JsonPath(response1);
		
		String message =js1.getString("msg");
		
		System.out.println("Success message is " +message);
		
		//Get
		
		String response2=given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeid)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.body("address",equalTo(newaddress))
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response2);
		
		JsonPath js2 = new JsonPath(response2);
		String newaddress1=js2.get("address");
		System.out.println(newaddress1);
		
		
		
		

	}

}
