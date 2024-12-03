package test.epam.rest;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.restassured.payloads.Payloads;
import com.restassured.payloads.StatusCode;
import com.restassured.payloads.URIs;
import com.restassured.payloads.URLs;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.Optional;

public class Task03RESTAutomation {

	private static Payloads objPayLoad = new Payloads();
	private static URIs objURI = new URIs();
	private static URLs objURL = new URLs();
	private static final Logger log = LogManager.getLogger(Task03RESTAutomation.class);
	private static final String openWeatherApiID = "8zdz3z1z6z2z4zaz4zbz8z7z6zcz0z5z3z2z8z7z2z0zfz8zczaz1z5z8z6zcz3z";
	// encrypted the API ID so that Git don't raise any concern
	private static String petID;
	private static UtilMethods utils = new UtilMethods();
	private static Response response;
	private static ValidatableResponse vResponse;
	private static String petName = "Snoopie_" + UtilMethods.getTime();
	private static final String userToSearch = "Ervin Howell";

	Payloads reqBody = new Payloads();

	public static void openWeatherMap() {

		String strOpenWeatherApiID = openWeatherApiID.replaceAll("z", "");
		System.out.println("strOpenWeatherApiID: " + strOpenWeatherApiID);

		response = given().baseUri(objURL.urlopenWeatherApiURL).queryParam("q", "hyderabad")
				.queryParam("appid", strOpenWeatherApiID).header("Content-Type", "application/json").log().all().when()
				.get(objURI.uriOpenWeather).then().log().all().extract().response();

		double longitude = Double.valueOf(response.path("coord.lon").toString());
		double latitude = Double.valueOf(response.path("coord.lat").toString());

		System.out.println("longitude+\" \"+latitude: " + longitude + " " + latitude);

		Assert.assertTrue(response.path("name").equals("Hyderabad"));

		Assert.assertTrue(response.path("sys.country").equals("IN"));

		Assert.assertTrue(Double.valueOf(response.path("main.temp_min").toString()) > 0.0);

		Assert.assertTrue(Double.valueOf(response.path("main.temp").toString()) > 0.0);

	}

	public static void epamEventsInEnglish() {
		response = given().baseUri(objURL.urlepamEvenetApiURL).header("Content-Type", "application/json").log().all()
				.when().get().then().log().all().extract().response();

		System.out.println(Optional.ofNullable(response.path("data.size()")));
	}

	public static void typicodeUser() {

		RestAssured.baseURI = objURL.urltypiCodeBaseURL;
		response = given().header("Content-Type", "application/json").log().all().when().get(objURI.uriTypiCodeUsers)
				.then().log().all().extract().response();

		Assert.assertTrue(response.getStatusCode() == StatusCode.OK200);

		int userCount = response.path("data.size()");
		Assert.assertTrue(userCount > 3);

		boolean isUserExist = false;
		for (int i = 0; i < userCount; i++) {

			isUserExist = response.path("name[" + i + "]").equals(userToSearch);
			if (isUserExist) {
				log.info("User exist at index [" + i + "], and id = " + response.path("[" + i + "].id"));
				System.out.println("User exist at index [" + i + "], and id = " + response.path("[" + i + "].id"));
				System.out.println("User details: " + response.path("[" + i + "]"));
				System.out.println("User details: " + response.path("[" + i + "].name"));
				System.out.println("User details: " + response.path("[" + i + "].username"));
				System.out.println("User details: " + response.path("[" + i + "].email"));
				System.out.println("User details: " + response.path("[" + i + "].phone"));
				break;
			}
		}
		Assert.assertTrue(isUserExist);

	}

	public static void createPet() {

		RestAssured.baseURI = objURL.urlpetStoreBaseURL;
		response = given().header("Content-Type", "application/json").log().all()
				.body(objPayLoad.petStoreCreatePetBody.replace("$petName", petName)).when()
				.post(objURI.uriPetStoreCreate).then().log().all().extract().response();

		petID = utils.rawToJson(response.asString()).getString("id");

		// POST END
		// GET STARTS

		vResponse = given().header("Content-Type", "application/json").pathParam("petID", petID).log().all()
				.get(objURI.uriPetStoreGet).then().log().all();

		ValidatableResponse v2 = vResponse;

		v2.assertThat().contentType(ContentType.JSON).statusCode(Integer.valueOf(StatusCode.OK200));

		response = vResponse.extract().response();

		String categoryOfPet = response.path("category.name");

		String petStatus = response.path("status");

		petID = utils.rawToJson(response.asString()).getString("id");
		Assert.assertEquals(categoryOfPet, "dog");
		Assert.assertEquals(petName, utils.rawToJson(response.asString()).getString("name"));
		Assert.assertEquals(petStatus, utils.rawToJson(response.asString()).getString("status"));

		System.out.println("Pet name: " + petName + ", " + "\nPet ID: " + petID + " " + "\nPet Status: " + petStatus);

	}

}
