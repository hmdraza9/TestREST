package test.epam.rest;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.restassured.payloads.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class Task03RESTAutomation {

	private static final Logger log = LogManager.getLogger(Task03RESTAutomation.class);
	private static final String petStoreBaseURL = "https://petstore.swagger.io";
	private static final String typiCodeBaseURL = "https://jsonplaceholder.typicode.com";
	private static final String epamEvenetApiURL = "https://events.epam.com/api/v2/events";
	private static final String openWeatherApiURL = "http://api.openweathermap.org";
	private static final String openWeatherApiID = "8d31624a4b876c05328720f8ca1586c3";
	private static String petID;
	private static UtilMethods utils = new UtilMethods();
	private static Response response;
	private static ValidatableResponse vResponse;
	private static testDataPayloads objPayLoad = new testDataPayloads();
	private static String petName = "Snoopie_" + UtilMethods.getTime();
	private static int statusCode;
	private static String contenttype;
	private static final String userToSearch = "Ervin Howell";

	testDataPayloads reqBody = new testDataPayloads();

	public static void openWeatherMap() {

		String resp = given().baseUri(openWeatherApiURL).queryParam("q", "hyderabad")
				.queryParam("appid", openWeatherApiID).header("Content-Type", "application/json").log().all().when()
				.get(objPayLoad.uriOpenWeather).then().log().all().extract().response().asString();

		JsonPath jp = utils.rawToJson(resp);

		double longitude = jp.getDouble("coord.lon");
		double latitude = jp.getDouble("coord.lat");

		System.out.println("longitude+\" \"+latitude: " + longitude + " " + latitude);

		resp = given().baseUri(openWeatherApiURL).queryParam("lat", latitude).queryParam("lon", longitude)
				.queryParam("appid", openWeatherApiID).header("Content-Type", "application/json").log().all().when()
				.get(objPayLoad.uriOpenWeather).then().log().all().extract().response().asString();

		jp = utils.rawToJson(resp);

		Assert.assertTrue(jp.getString("name").equals("Hyderabad"));

		Assert.assertTrue(jp.getString("sys.country").equals("IN"));

		Assert.assertTrue(jp.getDouble("main.temp_min") > 0.0);

		Assert.assertTrue(jp.getDouble("main.temp") > 0.0);

	}

	public static void epamEventsInEnglish() {
		String resp = given().baseUri(epamEvenetApiURL).header("Content-Type", "application/json").log().all().when()
				.get().then().log().all().extract().response().asString();

		JsonPath jp = utils.rawToJson(resp);

		System.out.println(jp.getInt("data.size()"));
	}

	public static void typicodeUser() {

		RestAssured.baseURI = typiCodeBaseURL;
		response = given().header("Content-Type", "application/json").log().all().when()
				.get(objPayLoad.uriTypiCodeUsers).then().log().all().extract().response();

		Assert.assertTrue(response.getStatusCode() == 200);

		JsonPath jp = utils.rawToJson(response.asString());
		int userCount = jp.getInt("data.size()");
		Assert.assertTrue(userCount > 3);

		boolean isUserExist = false;
		for (int i = 0; i < userCount; i++) {

			isUserExist = jp.getString("name[" + i + "]").equals(userToSearch);
			if (isUserExist) {
				log.info("User exist at [" + i + "]");
				break;
			}
		}
		Assert.assertTrue(isUserExist);

	}

	public static void createPet() {

		RestAssured.baseURI = petStoreBaseURL;
		response = given().header("Content-Type", "application/json").log().all()
				.body(objPayLoad.petStoreCreatePetBody.replace("$petName", petName)).when()
				.post(new testDataPayloads().uriPetStoreCreate).then().log().all().extract().response();

		petID = utils.rawToJson(response.asString()).getString("id");

		// POST complete here
		// GET follows

		vResponse = given().header("Content-Type", "application/json").pathParam("petID", petID).log().all()
				.get(objPayLoad.uriPetStoreGet).then().log().all();

		response = vResponse.extract().response();

		statusCode = response.getStatusCode();

		contenttype = response.getContentType();

		String categoryOfPet = utils.rawToJson(response.asString()).getString("category.name");

		String statusOfPet = utils.rawToJson(response.asString()).getString("status");

		petID = utils.rawToJson(response.asString()).getString("id");
		Assert.assertTrue(statusCode == 200);
		Assert.assertTrue(contenttype.contains("application/json"));
		Assert.assertTrue(categoryOfPet.equals("dog"));
		Assert.assertTrue(petName.equals(utils.rawToJson(response.asString()).getString("name")));
		Assert.assertTrue(statusOfPet.equals(utils.rawToJson(response.asString()).getString("status")));

		System.out.println("Pet name: " + petName + ", Pet ID: " + petID);

	}

}
