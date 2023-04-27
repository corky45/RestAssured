package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;

public class CreateOneProduct {
	String baseURI;
	SoftAssert softAssert;
	
	public CreateOneProduct() {
		baseURI = "https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
	}
	@Test
	public void createOneProduct() {
		
		/*
		 * given: all input
		 * details(baseURI,Headers,Authorization,Payload/Body,QueryParameters) when:
		 * submit api requests(Http method,Endpoint/Resource) then: validate
		 * response(status code, Headers, responseTime, Payload/Body)
		 */
		Response response = 
				given().baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("Authorization", "kjbufytfSIjfnsjhg")
				.body(new File("C:\\Users\\OfficeComRyzen\\Sept2023_Selenium\\RestAssured\\src\\main\\java\\data\\CreatePayload.json")).
				when()
				.post("/read_one.php").
				then()
				.extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code" + statusCode);
		Assert.assertEquals(200, statusCode);
		
		long responseTime = response.getTime();
		System.out.println("Response time is " + responseTime);
		if(responseTime <=2000) {
			System.out.println("Response time is within range.");
		}else {
			System.out.println("Response time is out of range.");
		}
		
		String responseHeaderContentType = response.getHeader("Content-Type");
		System.out.println("Response Header Content Type is" + responseHeaderContentType);
		//Assert.assertEquals(responseHeaderContentType, "application/json;charset=UTF-8");
		softAssert.assertEquals(responseHeaderContentType, "application/json; charset=UTF-8", "Content types are not matching");

		
		String responseBody = response.getBody().asString();
		JsonPath jp = new JsonPath(responseBody);
		String productMessage = jp.get("message");
		System.out.println("Product message is " + productMessage);
		softAssert.assertEquals(productMessage,"Product was created", "Product messages are not matching");
		
	
	}
}
