package com.restAPI.practiceClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookStoreOperations {

	private static final Logger log = LogManager.getLogger(BookStoreOperations.class);

	public static String toolsQABookStoreUsername = "TOOLSQA-Test";

	public static String toolsQABookStorePassword = "Test@@123";

	public static String toolsQABookStoreBearerToken;

	public static long toolsQABookStoreISBN = 9781449325862L;

	public static String baseURIToolsQABookStore = "https://bookstore.toolsqa.com";

	public static void main(java.lang.String[] args) {

		BookStoreOperations.fetchWeatherDetails();

		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		BookStoreOperations.generateAuthToken(baseURIToolsQABookStore, toolsQABookStoreUsername,
				toolsQABookStorePassword);
		BookStoreOperations.fetchUserBooks("/Book/v1/BooksStore", toolsQABookStoreUsername);

	}

	public static void fetchUserBooks(String pathParamQABookStore, String toolsQABookStoreUsername) {

		RestAssured.baseURI = baseURIToolsQABookStore;

		RequestSpecification request = RestAssured.given();

		String payLoad = "{\n" + "  \"userId\": \"" + toolsQABookStoreUsername + "\",\n"
				+ "  \"collectionOfIsbns\": [\n" + "    {\n" + "      \"isbn\": \"" + toolsQABookStoreISBN + "\"\n"
				+ "    }\n" + "  ]\n" + "}";

		request.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + toolsQABookStoreBearerToken).urlEncodingEnabled(false);

//		Response bookDetails = request.body(payLoad).post(pathParamQABookStore);
//		Response bookDetails = request.body(payLoad).redirects().follow(false).redirects().max(100).post(pathParamQABookStore);
		Response bookDetails = request.body(payLoad).post(pathParamQABookStore);

		bookDetails.prettyPrint();

		String respBody = bookDetails.getBody().asString();

		System.out.println("respBody: " + respBody + "; Status code: " + bookDetails.getStatusCode());

	}

	public static void generateAuthToken(String baseURIToolsQABookStore, String username, String password) {

		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		RestAssured.baseURI = baseURIToolsQABookStore;

		RequestSpecification request = RestAssured.given();

		String payLoad = "{\n" + "  \"userName\": \"" + username + "\",\n" + "  \"password\": \"" + password + "\"\n"
				+ "}";

		request.header("Content-Type", "application/json");

		Response genTokenForAuth = request.body(payLoad).post("/Account/v1/GenerateToken");

		genTokenForAuth.prettyPrint();

		String respBody = genTokenForAuth.getBody().asString();

		toolsQABookStoreBearerToken = JsonPath.from(respBody).get("token");

		System.out.println("Token: >" + toolsQABookStoreBearerToken + "<");

	}

	public static void fetchWeatherDetails() {

		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		Response response = RestAssured.given().get(
				"http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02");

		System.out.println("Call success?  :   " + (response.statusCode() == 200) + ";\n" + "Status Code is :   "
				+ response.statusCode());

		JsonPath jpath = response.jsonPath();

		System.out.println("coord.lon      :   " + jpath.get("coord.lon").toString());

		System.out.println("coord.lat      :   " + jpath.get("coord.lat").toString());

		System.out.println("Main           :   " + jpath.get("weather[0].main").toString());

		System.out.println("Description    :   " + jpath.get("weather[0].description").toString());

		System.out.println("Temperature    :   " + jpath.get("main.temp").toString());

		System.out.println("Pressure       :   " + jpath.get("main.pressure").toString());

		System.out.println("Humidity       :   " + jpath.get("main.humidity").toString());

		System.out.println("Visibility     :   " + jpath.get("visibility").toString());

		int visi = Integer.parseInt(jpath.get("visibility").toString());

		if (visi > 9000) {
			System.out.println("************ATC Ok************");
		} else {
			System.out.println("************ATC NOT Ok************");

		}
	}

}
