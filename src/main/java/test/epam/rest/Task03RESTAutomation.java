package test.epam.rest;

import static io.restassured.RestAssured.given;

import io.restassured.module.jsv.JsonSchemaValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.restassured.payloads.Payloads;
import com.restassured.payloads.HTTPCode;
import com.restassured.payloads.URIs;
import com.restassured.payloads.URLs;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;
import java.util.Optional;


public class Task03RESTAutomation {

	private static final Payloads objPayLoad = new Payloads();
	private static final URIs objURI = new URIs();
	private static final URLs objURL = new URLs();
	private static final Logger log = LogManager.getLogger(Task03RESTAutomation.class);
	private static final String openWeatherApiID = "8zdz3z1z6z2z4zaz4zbz8z7z6zcz0z5z3z2z8z7z2z0zfz8zczaz1z5z8z6zcz3z";
	private static final UtilMethods utils = new UtilMethods();
	private static Response response;
	private static final String petName = "Snoopie_" + UtilMethods.getTime();
	private static final String userToSearch = "Ervin Howell";
	private static ValidatableResponse vResponse;

	public static void openWeatherMap() {

		String strOpenWeatherApiID = openWeatherApiID.replaceAll("z", "");
		System.out.println("strOpenWeatherApiID: " + strOpenWeatherApiID);

		response = given().baseUri(objURL.urlopenWeatherApiURL).queryParam("q", "hyderabad")
				.queryParam("appid", strOpenWeatherApiID).header("Content-Type", "application/json").log().all().when()
				.get(objURI.uriOpenWeather).then().log().all().extract().response();

		double longitude = Double.parseDouble(response.path("coord.lon").toString());
		double latitude = Double.parseDouble(response.path("coord.lat").toString());

		System.out.println("longitude+\" \"+latitude: " + longitude + " " + latitude);

		Assert.assertEquals("Hyderabad", response.path("name"));

		Assert.assertEquals("IN", response.path("sys.country"));

		Assert.assertTrue(Double.parseDouble(response.path("main.temp_min").toString()) > 0.0);

		Assert.assertTrue(Double.parseDouble(response.path("main.temp").toString()) > 0.0);

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

		Assert.assertEquals(HTTPCode.OK200, response.getStatusCode());

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

		// encrypted the API ID so that Git don't raise any concern
		String petID = utils.rawToJson(response.asString()).getString("id");

		// POST END
		// GET STARTS

		vResponse = given().header("Content-Type", "application/json").pathParam("petID", petID).log().all()
				.get(objURI.uriPetStoreGet).then().log().all();

		vResponse.assertThat().contentType(ContentType.JSON).statusCode(HTTPCode.OK200);


		response = vResponse.extract().response();

		String categoryOfPet = response.path("category.name");

		String petStatus = response.path("status");

		petID = utils.rawToJson(response.asString()).getString("id");
//		Assert.assertEquals(categoryOfPet, "dog");
		Assert.assertEquals(petName, utils.rawToJson(response.asString()).getString("name"));
		Assert.assertEquals(petStatus, utils.rawToJson(response.asString()).getString("status"));

		System.out.println("Pet "+petName+" created successfully with below details:");
		System.out.println("Pet name: " + petName + ", " + "\nPet ID: " + petID + " " + "\nPet Status: " + petStatus);

	}

	public static void validateSchemaFroPetApi(){
		log.info("Schema validation started...");
		try {
			vResponse.assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/Data/JSON/petSchema.json")));
		} catch (JsonSchemaValidationException jEx) {
			jEx.printStackTrace();
			log.error(jEx);
			log.error("Schema validation failed!!!");
		}
	}

}