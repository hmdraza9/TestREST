package learn.udemy.site;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.restassures.api.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class udemyRest {

	public static final String baseURI = "https://rahulshettyacademy.com";

	public static final String mapKey = "qaclick123";

	public static String placeID;

	private static Set<String> placeSet;

	UtilMethods utils = new UtilMethods();

	testDataPayloads data = new testDataPayloads();

	public static udemyRest objRest = new udemyRest();

	public static void main(String[] args) {

		placeSet = new HashSet<String>();

//		objRest.perfAPI(objRest);

		objRest.bulkAddressAddDelete(objRest, 4);

		objRest.addPlace("OK");
		objRest.deletePlace("OK");

		// Course fee calculator
//		System.out.println(testDataPayloads.courseBody);
//		udemyRest.calcCourseFee();

	}

	// method overloading

	public void addPlace(String toVerify) {

		objRest.addPlace(toVerify, "999");

	}

	/*
	 * This method adds place method with dynamic JSON request and then deletes
	 * them. uses Set placeSet
	 */

	public void bulkAddressAddDelete(udemyRest objRest, int N) {

		for (int m : new UtilMethods().randBetween(100, 999, N)) {

			objRest.addPlace("OK", String.valueOf(m));
			System.out.println("Place ID: " + placeID);
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

			System.out.println("Iteration: " + i);

			objRest.addPlace("OK", "123");
			objRest.getPlace(200);
			objRest.deletePlace("OK");
			objRest.getPlace(404);
			objRest.addPlace("OK", "234");
			objRest.updatePlace("123, Street");
			objRest.getPlace(200);
		}
		endTime = System.currentTimeMillis();

		System.out.println("Total time taken: " + (endTime - startTime) / 1000 + " seconds");
	}

	// Adds place with the help of JSON Body request, saves place ID to global
	// variable placeID

	public void addPlace(String toVerify, String addressValue) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		RequestSpecification addPlaceReqSpec = given()
//				.log()
//				.all()
				.queryParam("key", mapKey)
				.header("Content-Type", "application/json")
				.urlEncodingEnabled(false);

		byte[] tempBody = null;
		try {
			tempBody = Files.readAllBytes(Paths.get("src/test/resources/AddPlaceBody.json"));
			addPlaceReqSpec.body(tempBody);
		} catch (Exception e) {
			e.printStackTrace();
			addPlaceReqSpec.body(testDataPayloads.addPlaceBody.replace("#address#", addressValue));
		}

		System.out.println("***************Request starts:***************");

		Response addPlaceResp = addPlaceReqSpec.when().post(testDataPayloads.uriAddPlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		addPlaceResp.then()
//		.log()
//		.all()
				.assertThat().statusCode(200).body("scope", equalTo("APP")).body("status", equalTo(toVerify));

		placeID = addPlaceResp.getBody().path("place_id");
		placeSet.add(placeID);

		String response = addPlaceResp
				.then()
				.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)")
				.extract()
				.response()
				.asString();

//		System.out.println("\nAdd Place Response: " + response);

//		System.out.println("\n\nPlace ID(using get.path): " + placeID);

		System.out.println("\n\nPlace ID(using JsonPath): " + utils.rawToJson(response).getString("place_id"));

		System.out.println("\naddPlace.statusCode: " + addPlaceResp.statusCode());
	}

	public void getPlace(int toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response getPlaceResp = given()
//				.log()
//				.all()
				.queryParam("key", mapKey)
				.header("Content-Type", "application/json")
				.queryParam("place_id", placeID)
				.urlEncodingEnabled(false).when()
				.get(testDataPayloads.uriGetPlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

//		System.out.println("getPlaceResp.asString: " + getPlaceResp.asString());

		String response = getPlaceResp.then()
//				.log()
//				.all()
				.assertThat()
				.statusCode(toVerify)
				.extract()
				.response()
				.asString();
		System.out.println("\nGet Place Response: " + response);

//		System.out.println("Location - latitude : " + utils.rawToJson(response).getString("location.latitude"));

//		System.out.println("Location - longitude: " + utils.rawToJson(response).getString("location.longitude"));

		System.out.println("Address             : " + utils.rawToJson(response).getString("address"));
	}

	public void deletePlace(String toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response response = given()
//				.log()
//				.all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.urlEncodingEnabled(false)
				.body(testDataPayloads.deletePlaceBody.replace("$RunTimeVar1", placeID))
				.when()
				.post(testDataPayloads.uriDeletePlace);
		System.out.println("\n\n***************Response starts***************n\n\n");

		System.out.println("deletePlaceResp.asString: " + response.asString());

		response
				.then()
		//		.log()
		//		.all()
				.assertThat()
				.statusCode(200)
				.body("status", equalTo(toVerify));

		System.out.println("***************Place deleted successfully!***************");

	}

	public void updatePlace(String newAddress) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response response = given()
//				.log()
//				.all()
				.queryParam("key", mapKey)
				.header("Content-Type", "application/json")
				.queryParam("place_id", placeID)
				.urlEncodingEnabled(false)
				.body(testDataPayloads.updatePlaceBody.replace("$RunTimeVar1", placeID).replace("$RunTimeVar2", newAddress).replace("$RunTimeVar3", mapKey))
				.when()
				.put(testDataPayloads.uriUpdatePlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		System.out.println("getPlaceResp.asString: " + response.asString());

		response
				.then()
				.assertThat()
				.statusCode(200)
				.body("msg", equalTo("Address successfully updated"))
				.extract()
				.response()
				.asString();

		System.out.println("\nUpdate Place Response: " + response);

	}

	public void calcCourseFee() {

		int courseSize = utils.rawToJson(data.courseBody).getInt("courses.size()");

		int coursesSum = 0;

		String coursesTitle = "";

		int coursesPrice = 0;

		int coursesCopies = 0;

		int coursesPurchaseAmount = utils.rawToJson(data.courseBody).getInt("dashboard.purchaseAmount");

		String coursesPriceLabel = "";

//		System.out.println(UtilMethods.rawToJson(testDataPayloads.courseBody).get("dashboard"));

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

			System.out.println("\t\tCopies: " + coursesCopies + " -- This course is " + coursesPriceLabel);

		}

		System.out.println("Total price: " + coursesSum);
		System.out.println("Is sum equal: " + (coursesSum == coursesPurchaseAmount));

	}
}
