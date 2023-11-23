package learn.udemy.site;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restassures.api.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class udemyRest {

	public static final String baseURI = "https://rahulshettyacademy.com";

	public static final String mapKey = "qaclick123";

	public static String placeID;

	public static final Logger log = LogManager.getFormatterLogger(udemyRest.class);

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;

		for (int i = 0; i < 50; i++) {
			
			System.out.println("Iteration: "+i);

			udemyRest.addPlace("OK");
			udemyRest.getPlace(200);
			udemyRest.deletePlace("OK");
			udemyRest.getPlace(404);
			udemyRest.addPlace("OK");
			udemyRest.updatePlace("123, Street");
			udemyRest.getPlace(200);
			log.info("in add place method");
		}
		endTime = System.currentTimeMillis();

		System.out.println("Total time taken: " + (endTime - startTime) / 1000 + " seconds");

		// Course fee calculator
//		System.out.println(testDataPayloads.courseBody);
//		udemyRest.calcCourseFee();

	}

	public static void addPlace(String toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		log.info("in add place method");

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response addPlaceResp = given().log().all().queryParam("key", mapKey).header("Content-Type", "application/json")
				.urlEncodingEnabled(false).body(testDataPayloads.addPlaceBody).when()
				.post(testDataPayloads.uriAddPlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		addPlaceResp.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).body("status",
				equalTo(toVerify));

		placeID = addPlaceResp.getBody().path("place_id");

		String response = addPlaceResp.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		System.out.println("\nAdd Place Response: " + response);

		System.out.println("\n\nPlace ID(using get.path): " + placeID);

		System.out.println("\n\nPlace ID(using JsonPath): " + UtilMethods.rawToJson(response).getString("place_id"));

		System.out.println("\naddPlace.statusCode: " + addPlaceResp.statusCode());
	}

	public static void getPlace(int toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response getPlaceResp = given().log().all().queryParam("key", mapKey).header("Content-Type", "application/json")
				.queryParam("place_id", placeID).urlEncodingEnabled(false).when().get(testDataPayloads.uriGetPlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		System.out.println("getPlaceResp.asString: " + getPlaceResp.asString());

		String response = getPlaceResp.then().log().all().assertThat().statusCode(toVerify).extract().response()
				.asString();
		System.out.println("\nGet Place Response: " + response);

		System.out.println("Location - latitude : " + UtilMethods.rawToJson(response).getString("location.latitude"));

		System.out.println("Location - longitude: " + UtilMethods.rawToJson(response).getString("location.longitude"));

		System.out.println("Address             : " + UtilMethods.rawToJson(response).getString("address"));
	}

	public static void deletePlace(String toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response response = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").urlEncodingEnabled(false)
				.body(testDataPayloads.deletePlaceBody.replace("$RunTimeVar1", placeID)).when()
				.post(testDataPayloads.uriDeletePlace);
		System.out.println("\n\n***************Response starts***************n\n\n");

		System.out.println("deletePlaceResp.asString: " + response.asString());

		response.then().log().all().assertThat().statusCode(200).body("status", equalTo(toVerify));

		System.out.println("\nDelete Place Response: " + response);

		System.out.println("***************Place deleted successfully!***************");

	}

	public static void updatePlace(String newAddress) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response response = given().log().all().queryParam("key", mapKey).header("Content-Type", "application/json")
				.queryParam("place_id", placeID).urlEncodingEnabled(false)
				.body(testDataPayloads.updatePlaceBody.replace("$RunTimeVar1", placeID)
						.replace("$RunTimeVar2", newAddress).replace("$RunTimeVar3", mapKey))
				.when().put(testDataPayloads.uriUpdatePlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		System.out.println("getPlaceResp.asString: " + response.asString());

		response.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract()
				.response().asString();

		System.out.println("\nUpdate Place Response: " + response);

	}

	public static void calcCourseFee() {

		int courseSize = UtilMethods.rawToJson(testDataPayloads.courseBody).getInt("courses.size()");

		int coursesSum = 0;

		String coursesTitle = "";

		int coursesPrice = 0;

		int coursesCopies = 0;

		int coursesPurchaseAmount = UtilMethods.rawToJson(testDataPayloads.courseBody)
				.getInt("dashboard.purchaseAmount");

		String coursesPriceLabel = "";

//		System.out.println(UtilMethods.rawToJson(testDataPayloads.courseBody).get("dashboard"));

		for (int i = 0; i < courseSize; i++) {

			coursesTitle = UtilMethods.rawToJson(testDataPayloads.courseBody).getString("courses[" + i + "].title");
			coursesPrice = UtilMethods.rawToJson(testDataPayloads.courseBody).getInt("courses[" + i + "].price");
			coursesCopies = UtilMethods.rawToJson(testDataPayloads.courseBody).getInt("courses[" + i + "].copies");

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
