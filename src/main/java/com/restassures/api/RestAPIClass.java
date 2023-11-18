package com.restassures.api;
//import org.testng.AssertJUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestAPIClass {

	static JsonPath jp;
	static Response resp;

	public static void main(String[] args) throws IOException {
//		RestAPIClass.myFirstRESTMethod();
//		RestAPIClass.mySecondRESTMethod(0);
		RestAPIClass.myPOSTRESTMethod();
	}

	public static void myPOSTRESTMethod() throws IOException {

		String reqBody = "src/test/resources/jsondemo.json";
//		String reqBody = "src/test/resources/xmldemo.xml";
		File file = new File(reqBody);
		System.out.println(file.exists());

		byte[] b = Files.readAllBytes(Paths.get(reqBody));

		String jsonData = new String(b);

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

		Response response = given().headers("Content-type", "application/json").body(jsonData)

//				.when().get("/users")
				.when().post("/posts");

		response.then().assertThat().statusCode(201).log().all();
		System.out.println("Headers size: " + response.getHeaders().size());

	}

	public static void myFirstRESTMethod() {

		// TODO Auto-generated method stub
		String baseURI = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02";
		String guru99BaseURI = "http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";
		RestAssured.baseURI = baseURI;
		resp = RestAssured.get();

		int code = resp.getStatusCode();

		String data = resp.asString();

		System.out.println("The status code is :" + code);

//		AssertJUnit.assertEquals(code, 200);

		System.out.println("1. The data is " + data);
		System.out.println("2. The response time is :" + resp.getTime());
		System.out.println("3. contentType: " + resp.contentType());
		System.out.println("4. getContentType: " + resp.getContentType());
		System.out.println("5. getSessionId: " + resp.getSessionId());
		System.out.println("6. getStatusLine: " + resp.getStatusLine());
		System.out.println("7. getTime: " + resp.getTime());
		System.out.println("8. prettyPrint: " + resp.prettyPrint());
		System.out.println("9. headers: " + resp.headers());
		System.out.println("10. header: " + resp.header("Connection"));
		jp = resp.jsonPath();
		System.out.println("11. resp pretty: " + resp.asString());
		System.out.println("12. jp.get('cod'): " + jp.get("cod").toString());
		System.out.println("13. jp.get('message'): " + jp.get("message").toString());

		RestAssured.baseURI = guru99BaseURI;
		resp = RestAssured.get();
		System.out.println("\n\n\nURI: " + guru99BaseURI + "\nresponse:\n" + resp.asString());

	}

	public static void mySecondRESTMethod(int count) {

		// without query parameter
		String baseURI1 = "http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";
//
//		given().when().get(baseURI).then().log().all();

		// with query parameter
		baseURI = "http://demo.guru99.com/V4/sinkministatement.php";
		given().queryParam("CUSTOMER_ID", "68195").queryParam("PASSWORD", "1234!").queryParam("Account_No", "1").when()
				.get(baseURI).then().log().body();

//		System.out.println("Status code: " + given().when().get(baseURI).statusCode());
//		System.out
//				.println("Status code: " + given().when().get(baseURI).timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
//		System.out.println(given().when().get(baseURI).then().assertThat().statusCode(200));

		for (int i = 1; i <= count; i++) {
			System.out.print(i + ". Status code: " + given().when().get(baseURI).statusCode() + "; ");
			System.out.println(
					"Status code: " + given().when().get(baseURI).timeIn(TimeUnit.MILLISECONDS) + " milliseconds");

		}

		int sumAmount = 0;
//		ArrayList<String> amountArr = given().when().get(baseURI1).then().extract().path("result.statements.AMOUNT");
////										when().get(baseURI1).then().extract().path("result.stements.AMOUNT");
//		for (String str : amountArr) {
//
//			sumAmount = sumAmount + Integer.valueOf(str);
//
//		}
		System.out.println("Sum of amounts: " + sumAmount);

		Response resp = given().when().get(baseURI1);
		System.out.println("1. resp.asString(): " + resp);
		System.out.println("2. resp.getBody(): " + resp.getBody().htmlPath());
		System.out.println("3. resp.print(): " + resp.print());

//		System.out.println("4. given().contentType(ContentType.JSON).log().all(): "+given().contentType(ContentType.JSON).log().all());
		System.out.println("4. given().contentType(ContentType.JSON).log().all(): " + RestAssured.get());

		JsonPath jpe = resp.jsonPath();
		String amount = jpe.prettyPrint();
		System.out.println("Amount : " + amount);

	}
}
