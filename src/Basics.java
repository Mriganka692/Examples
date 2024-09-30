import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;  // required for given method.all static packages will be imported

import static org.hamcrest.Matchers.*;  // required for 'equalTo' to validate body response in then() method

import org.testng.Assert;

import files.payload;

public class Basics {

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
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response =given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		// we have created a separate package called payload.java to call the content of the body
		.when().post("maps/api/place/add/json") // we are passing post method along the resource in then() method
		.then().log().all().assertThat().statusCode(200)
		
		.body("scope", equalTo("APP")) // body ASSERTION to check the key value of scope is 'App' or not as found in the response body of POST request
		.header("Server", "Apache/2.4.52 (Ubuntu)") //header assertion to check if server name is correct as found in the post response

        .extract().response().asString(); //extracting response from the json output as string, hence assigned a string variable
		
		System.out.println(response);  //printing the response
		
		JsonPath js = new JsonPath(response);  // parsing the json, passing the variable response as an argument
		
		String placeid=js.getString("place_id");  // extracting place_id value and assigning it to a variable
		
		System.out.println(placeid+ "is the extracted place id from the json response"); //priinting the place id
		
		// update place
		
		String newaddress1 = "Summer walk, Africa"; //new address to update
		
		String response1 =given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"  // we pass placeid variable which we extarcted in the previous json parsing
				// to pass a variable, we write it as "+placeid+" so that it is not treated as a String
				+ "\"address\":\""+newaddress1+"\",\r\n" // passing the address variable to update the same during run time
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json").then().log().all()
		.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response1);
		
		JsonPath js1 = new JsonPath(response1);
		
		String message =js1.getString("msg");
		
		System.out.println("content in the response is " + message);
		
		//Get process
		
		String response2=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid) //using the placeid variable which we got from previous steps
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response2);
		
		JsonPath js2 = new JsonPath(response2);
		String newaddress=js2.getString("address");
		System.out.println("Updated address is" + " " +newaddress);
		String newlocation =js2.getString("location");
		String newlocation1 =js2.getString("location.latitude");
		String newlocation2 =js2.getString("location.longitude");
		System.out.println("Location is" + " " +  newlocation2);
		Assert.assertEquals(newaddress, newaddress1);
		//Assert.assertEquals(newaddress, "india"); ///it will fail
		
		
	}

}
