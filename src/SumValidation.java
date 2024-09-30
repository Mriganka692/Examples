import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test           //we creating a testng test case, so we are not giving public static main argument
	
	public void sumOfCourses() {
		
		int sum =0;
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
	
		
		int count =js.getInt("courses.size()");
		
		for (int i=0;i<count;i++) {
			
			//String title=js.get("course["+i+"].title");
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int amount = price*copies;
			System.out.println(amount);
			
			sum = sum + amount;
			
			break;
			
			
		}
		System.out.println(sum);
		
		int pamount =js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, pamount);
		
		
}
	
	

}
