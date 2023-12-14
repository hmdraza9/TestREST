package learn.udemy.site;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restassures.api.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import test.oauth.REST.TestOAuthRestAPI;

public class udemyRest {
	private static final Logger log = LogManager.getLogger(udemyRest.class);

	public static final String baseURI = "https://rahulshettyacademy.com";
	
	public static final String mapKey = "qaclick123";

	public static String placeID;

	private static Set<String> placeSet;

	UtilMethods utils = new UtilMethods();

	testDataPayloads data = new testDataPayloads();

	public static udemyRest objRest = new udemyRest();

	public static void main(String[] args) throws IOException {
		
		
		TestOAuthRestAPI objOAuth = new TestOAuthRestAPI();
		
//		objOAuth.getOAuthCode();
		
		objOAuth.testOAuthRestAssured();

//		placeSet = new HashSet<String>();
//
//		objRest.bulkAddressAddDelete(objRest, 1);
//
//		objRest.addPlace("OK");
//		objRest.deletePlace("OK");
	}

	// method overloading

	public void addPlace(String toVerify) {
		log.info("Method overloading - " + new Throwable().getStackTrace()[0].getMethodName());
		objRest.addPlace(toVerify, "999");

	}

	/*
	 * This method adds place method with dynamic JSON request and then deletes
	 * them. uses Set placeSet
	 */

	public void bulkAddressAddDelete(udemyRest objRest, int N) {

		for (int m : new UtilMethods().randBetween(100, 999, N)) {

			objRest.addPlace("OK", String.valueOf(m));
			log.info("Place ID: " + placeID);
//			objRest.getPlace(200);

		}

		for (String str : placeSet) {

			placeID = str;

			objRest.deletePlace("OK");
			objRest.getPlace(404);

		}
	}

	/*
	 * This method callsaddPlace/getPlace/DeletePlace/updatePlace N times and print
	 * total time
	 * 
	 */

	public void perfAPI(udemyRest objRest) {

		long startTime = System.currentTimeMillis();
		long endTime = 0;

		for (int i = 0; i < 2; i++) {

			log.info("Iteration: " + i);

			objRest.addPlace("OK", "123");
			objRest.getPlace(200);
			objRest.deletePlace("OK");
			objRest.getPlace(404);
			objRest.addPlace("OK", "234");
			objRest.updatePlace("123, Street");
			objRest.getPlace(200);
		}
		endTime = System.currentTimeMillis();

		log.info("Total time taken: " + (endTime - startTime) / 1000 + " seconds");
	}

	// Adds place with the help of JSON Body request, saves place ID to global
	// variable placeID

	public void addPlace(String toVerify, String addressValue) {
		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		RestAssured.baseURI = baseURI;

		RequestSpecification addPlaceReqSpec = given()
				.log()
				.all()
				.queryParam("key", mapKey).header("Content-Type", "application/json").urlEncodingEnabled(false);

		byte[] tempBody = null;
		try {
			tempBody = Files.readAllBytes(Paths.get("src/test/resources/AddPlaceBody.json"));
			addPlaceReqSpec.body(tempBody);
		} catch (Exception e) {
			e.printStackTrace();
			addPlaceReqSpec.body(testDataPayloads.addPlaceBody.replace("#address#", addressValue)); 
		}

		log.info("###########Request starts:###########");

		Response addPlaceResp = addPlaceReqSpec.when().post(testDataPayloads.uriMapAddPlace);

		log.info("###########Response starts###########");

		addPlaceResp.then()
//		.log()
//		.all()
				.assertThat().statusCode(200).body("scope", equalTo("APP")).body("status", equalTo(toVerify));

		placeID = addPlaceResp.getBody().path("place_id");
		placeSet.add(placeID);

		String response = addPlaceResp.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

//		log.info("Add Place Response: " + response);

//		log.info("Place ID(using get.path): " + placeID);

		log.info("Place ID(using JsonPath): " + utils.rawToJson(response).getString("place_id"));

		log.info("addPlace.statusCode: " + addPlaceResp.statusCode());
	}

	public void getPlace(int toVerify) {
		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		RestAssured.baseURI = baseURI;

		log.info("###########Request starts:###########");

		Response getPlaceResp = given()
//				.log()
//				.all()
				.queryParam("key", mapKey).header("Content-Type", "application/json").queryParam("place_id", placeID)
				.urlEncodingEnabled(false).when().get(testDataPayloads.uriMapGetPlace);

		log.info("###########Response starts###########n");

//		log.info("getPlaceResp.asString: " + getPlaceResp.asString());

		String response = getPlaceResp.then()
//				.log()
//				.all()
				.assertThat().statusCode(toVerify).extract().response().asString();
		log.info("Get Place Response: " + response);

//		log.info("Location - latitude : " + utils.rawToJson(response).getString("location.latitude"));

//		log.info("Location - longitude: " + utils.rawToJson(response).getString("location.longitude"));

		log.info("Address             : " + utils.rawToJson(response).getString("address"));
	}

	public void deletePlace(String toVerify) {
		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		RestAssured.baseURI = baseURI;

		log.info("###########Request starts:###########");

		Response response = given()
//				.log()
//				.all()
				.queryParam("key", "qaclick123").header("Content-Type", "application/json").urlEncodingEnabled(false)
				.body(testDataPayloads.deletePlaceBody.replace("$RunTimeVar1", placeID)).when()
				.post(testDataPayloads.uriMapDeletePlace);
		log.info("###########Response starts###########n");

		log.info("deletePlaceResp.asString: " + response.asString());

		response.then()
				// .log()
				// .all()
				.assertThat().statusCode(200).body("status", equalTo(toVerify));

		log.info("###########Place deleted successfully!###########");

	}

	public void updatePlace(String newAddress) {
		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		RestAssured.baseURI = baseURI;

		log.info("###########Request starts:###########");

		Response response = given()
//				.log()
//				.all()
				.queryParam("key", mapKey).header("Content-Type", "application/json").queryParam("place_id", placeID)
				.urlEncodingEnabled(false)
				.body(testDataPayloads.updatePlaceBody.replace("$RunTimeVar1", placeID)
						.replace("$RunTimeVar2", newAddress).replace("$RunTimeVar3", mapKey))
				.when().put(testDataPayloads.uriMapUpdatePlace);

		log.info("###########Response starts###########");

		log.info("getPlaceResp.asString: " + response.asString());

		response.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract()
				.response().asString();

		log.info("Update Place Response: " + response);

	}

	public void calcCourseFee() {

		int courseSize = utils.rawToJson(data.courseBody).getInt("courses.size()");

		int coursesSum = 0;

		String coursesTitle = "";

		int coursesPrice = 0;

		int coursesCopies = 0;

		int coursesPurchaseAmount = utils.rawToJson(data.courseBody).getInt("dashboard.purchaseAmount");

		String coursesPriceLabel = "";

//		log.info(UtilMethods.rawToJson(testDataPayloads.courseBody).get("dashboard"));

		for (int i = 0; i < courseSize; i++) {

			coursesTitle = utils.rawToJson(data.courseBody).getString("courses[" + i + "].title");
			coursesPrice = utils.rawToJson(data.courseBody).getInt("courses[" + i + "].price");
			coursesCopies = utils.rawToJson(data.courseBody).getInt("courses[" + i + "].copies");

			coursesSum = coursesSum + coursesPrice * coursesCopies;

			if (coursesPrice > 40) {
				coursesPriceLabel = "COSTLY";
			} else {
				coursesPriceLabel = "MEDIUM";
			}

			System.out.print("Title: " + coursesTitle);

			System.out.print("\t\tPrice: " + coursesPrice);

			log.info("\t\tCopies: " + coursesCopies + " -- This course is " + coursesPriceLabel);

		}

		log.info("Total price: " + coursesSum);
		log.info("Is sum equal: " + (coursesSum == coursesPurchaseAmount));

	}
}
