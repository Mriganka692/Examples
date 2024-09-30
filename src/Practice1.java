import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;


public class Practice1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// given , when , then
				//given - input the details
				//when - submit the API , resource and http method
				//then - validate the response
				
				// url to test -https://rahulshettyacademy.com/maps/api/place/add/json?key=qaclick123
				//base url - https://rahulshettyacademy.com
				//query parameter - key=qaclick123
				//post(resource name = maps/api/place/add/json)
				// log().all(). is used in given() and then() methods to log all the request and response logs respectively
		       
		//Add place > update place with new address > get place to validate if new address is present in response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		// we have created a separate package called payload.java to call the content of the body
		.when().post("/maps/api/place/add/json")
		.then().log().all()
		.assertThat().statusCode(200).body("scope", equalTo("APP"))
		
		// body ASSERTION to check the key value of scope is 'App' or not as found in the response body of POST request
	.header("Server", "Apache/2.4.52 (Ubuntu)")
		//header assertion to check the header server name is the one found in the POST response body
	.extract().response().asString(); // since we are extracting the response as a string, we assign it as a string variable in line 27
	
		System.out.println(response);
		// if we remove log().all() from then and donot execute syso(response), console will not display the response
		//if we remove log().all() from then and  execute syso(response), console will  display the response
		
		JsonPath js = new JsonPath(response);// parsing the json
		//passing the variable 'response' as an argument
		//extract information from json and place it in the code
		//parsed values are now stores in the js object
		
		js.get("place_id"); // extracting place_id from json response
		
		
		
		String placeId = js.getString("place_id");//whatever value got extracted, we will place it in a variable
		
		System.out.println("place id is " +placeId); // print the place ID
		
		// update
		
		String newaddress = "first walk,India";
		
		String response1=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
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
		
		// get method
		String response2=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId )
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200)
		.body("address", equalTo(newaddress))
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response2);
		
		JsonPath js2 = new JsonPath(response2);
		
		//JsonPath js2=ReusableMethods.rawtoJson(response2); //covert raw string to json file
		String addressnew=js2.getString("address"); // extracting address from json response and assigning a value
		
		System.out.println("New address is " +addressnew);  //actual address
		
		String newlocation = js2.getString("location");
		String newlocation1 = js2.getString("location.latitude");
		System.out.println(newlocation);
		
		//String newaddress = "first walk,India";  //expected address
		
		Assert.assertEquals(addressnew, newaddress); //testng jar used to compare actual value with expected
		
		
		
	
	}

}
