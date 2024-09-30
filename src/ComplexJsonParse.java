import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		int sum =0 ;
		
		// print no . of courses returned by the API
		int count =js.getInt("courses.size()");
		System.out.println(count);
		
		// print purchase amount
		int amount =js.getInt("dashboard.purchaseAmount"); // if purchase amount was n double quotes, it would be an integer
		System.out.println(amount);
		
		// print website
		String website=js.getString("dashboard.website");
		System.out.println(website);
		
		// print the title of the first course
		String firstitle=js.getString("courses[0].title");  //course[0] since index of first course is 0
		System.out.println(firstitle);
		
		// print the title of the third course
		String thirdtitle=js.getString("courses[2].title");  //course[2] since index of third course is 2
		System.out.println(thirdtitle);
		
		//for all course titles and prices
		for (int i=0;i<count;i++)
		{
			String coursetitles=js.get("courses["+i+"].title"); //give course[] to specify the index
			// System.out.println(js.get("courses["+i+"].price").toString())  ; //because sysout always expect string
			 int copies =js.get("courses["+i+"].copies");
			 
			 //System.out.println(copies);
			 
			 //System.out.println(coursetitles);
			 
			 int price = js.get("courses["+i+"].price");
			 
			 int totalprice = price*copies;
			 
			 System.out.println(totalprice);
			 
			 sum = sum + totalprice;
			 
			 System.out.println(sum);
			 
			 //System.out.println("Title of the course is" + " "+ coursetitles + "having total price as" + " " + totalprice);
	
		}
		int pamount=js.getInt("dashboard.purchaseAmount");
		System.out.println(pamount);
		Assert.assertEquals(sum, pamount); //compare the calculated and set sum value
		
		//System.out.println("no of copies sold by RPA");
		
		for ( int i =0;i<count;i++) {
			String coursetitles=js.getString("courses["+i+"].title");
			//if(coursetitles.contentEquals("RPA"))
				if(coursetitles.equalsIgnoreCase("RPA"))
			{
				System.out.println("Copies sold by RPA are " + js.get("courses["+i+"].copies").toString());
			break;
			}
		}
		
		
		
		
		
		

	}

}
