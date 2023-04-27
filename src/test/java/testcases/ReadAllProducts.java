 package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProducts {
	@Test
	public void readAllProducts() {
		/*
		 * given: all input
		 * details(baseURI,Headers,Authorization,Payload/Body,QueryParameters) when:
		 * submit api requests(Http method,Endpoint/Resource) then: validate
		 * response(status code, Headers, responseTime, Payload/Body)
		 */
		Response response = 
				given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
				.basic("demo@techfios.com", "abc123").
				// .log().all()
				when()
				// .log().all()
				.get("/read.php").
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
		Assert.assertEquals(responseHeaderContentType, "application/json; charset=UTF-8");
		String responseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(responseBody);
		String firstProductID = jp.get("records[0].id");
		System.out.println("First product ID is" + firstProductID);
		
		if(firstProductID != null) {
			System.out.println("First product is not null.");
		}else {
			System.out.println("First product is null.");
		}
	}
}
