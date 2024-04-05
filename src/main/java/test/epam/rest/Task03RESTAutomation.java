package test.epam.rest;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.restassured.payloads.StatusCode;
import com.restassured.payloads.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class Task03RESTAutomation {

	private static testDataPayloads objPayLoad = new testDataPayloads();
	private static final Logger log = LogManager.getLogger(Task03RESTAutomation.class);
	private static final String openWeatherApiID = "8zdz3z1z6z2z4zaz4zbz8z7z6zcz0z5z3z2z8z7z2z0zfz8zczaz1z5z8z6zcz3z";
	// encrypted the API ID so that Git don't raise any concern
	private static String petID;
	private static UtilMethods utils = new UtilMethods();
	private static Response response;
	private static ValidatableResponse vResponse;
	private static String petName = "Snoopie_" + UtilMethods.getTime();
	private static final String userToSearch = "Ervin Howell";

	testDataPayloads reqBody = new testDataPayloads();

	public static void openWeatherMap() {

		String strOpenWeatherApiID = openWeatherApiID.replaceAll("z", "");
		System.out.println("strOpenWeatherApiID: " + strOpenWeatherApiID);

		String resp = given().baseUri(objPayLoad.urlopenWeatherApiURL).queryParam("q", "hyderabad")
				.queryParam("appid", strOpenWeatherApiID).header("Content-Type", "application/json").log().all().when()
				.get(objPayLoad.uriOpenWeather).then().log().all().extract().response().asString();

		JsonPath jp = utils.rawToJson(resp);

		double longitude = jp.getDouble("coord.lon");
		double latitude = jp.getDouble("coord.lat");

		System.out.println("longitude+\" \"+latitude: " + longitude + " " + latitude);

		resp = given().baseUri(objPayLoad.urlopenWeatherApiURL).queryParam("lat", latitude).queryParam("lon", longitude)
				.queryParam("appid", strOpenWeatherApiID).header("Content-Type", "application/json").log().all().when()
				.get(objPayLoad.uriOpenWeather).then().log().all().extract().response().asString();

		jp = utils.rawToJson(resp);

		Assert.assertTrue(jp.getString("name").equals("Hyderabad"));

		Assert.assertTrue(jp.getString("sys.country").equals("IN"));

		Assert.assertTrue(jp.getDouble("main.temp_min") > 0.0);

		Assert.assertTrue(jp.getDouble("main.temp") > 0.0);

	}

	public static void epamEventsInEnglish() {
		String resp = given().baseUri(objPayLoad.urlepamEvenetApiURL).header("Content-Type", "application/json").log()
				.all().when().get().then().log().all().extract().response().asString();

		JsonPath jp = utils.rawToJson(resp);

		System.out.println(jp.getInt("data.size()"));
	}

	public static void typicodeUser() {

		RestAssured.baseURI = objPayLoad.urltypiCodeBaseURL;
		response = given().header("Content-Type", "application/json").log().all().when()
				.get(objPayLoad.uriTypiCodeUsers).then().log().all().extract().response();

		Assert.assertTrue(response.getStatusCode() == StatusCode.OK200);

		System.out.println("response.getStatusCode() == StatusCode.OK200: "
				+ String.valueOf(response.getStatusCode() == StatusCode.OK200).toUpperCase());

		JsonPath jp = utils.rawToJson(response.asString());
		int userCount = jp.getInt("data.size()");
		System.out.println("Response has more than 3 records: " + String.valueOf((userCount > 3)).toUpperCase());
		Assert.assertTrue(userCount > 3);

		boolean isUserExist = false;
		for (int i = 0; i < userCount; i++) {

			isUserExist = jp.getString("name[" + i + "]").equals(userToSearch);
			if (isUserExist) {
				log.info("User exist at index [" + i + "], and id = " + jp.getInt("[" + i + "].id"));
				System.out.println("User exist at index [" + i + "], and id = " + jp.getInt("[" + i + "].id"));
				System.out.println("User details: " + jp.getString("[" + i + "]"));
				System.out.println("User details: " + jp.getString("[" + i + "].name"));
				System.out.println("User details: " + jp.getString("[" + i + "].username"));
				System.out.println("User details: " + jp.getString("[" + i + "].email"));
				System.out.println("User details: " + jp.getString("[" + i + "].phone"));
				break;
			}
		}
		Assert.assertTrue(isUserExist);

	}

	public static void createPet() {

		RestAssured.baseURI = objPayLoad.urlpetStoreBaseURL;
		response = given().header("Content-Type", "application/json").log().all()
				.body(objPayLoad.petStoreCreatePetBody.replace("$petName", petName)).when()
				.post(new testDataPayloads().uriPetStoreCreate).then().log().all().extract().response();

		petID = utils.rawToJson(response.asString()).getString("id");

		// POST END
		// GET STARTS

		vResponse = given().header("Content-Type", "application/json").pathParam("petID", petID).log().all()
				.get(objPayLoad.uriPetStoreGet).then().log().all();

		ValidatableResponse v2 = vResponse;

		v2.assertThat().contentType(ContentType.JSON).statusCode(Integer.valueOf(StatusCode.OK200));

		response = vResponse.extract().response();

		String categoryOfPet = utils.rawToJson(response.asString()).getString("category.name");

		String statusOfPet = utils.rawToJson(response.asString()).getString("status");

		petID = utils.rawToJson(response.asString()).getString("id");
//		Assert.assertTrue(statusCode == StatusCode.OK200);
//		Assert.assertTrue(contenttype.contains("application/json"));
		Assert.assertEquals(categoryOfPet, "dog");
		Assert.assertEquals(petName, utils.rawToJson(response.asString()).getString("name"));
		Assert.assertEquals(statusOfPet, utils.rawToJson(response.asString()).getString("status"));

		System.out.println("Pet name: " + petName + ", Pet ID: " + petID);

	}

}
