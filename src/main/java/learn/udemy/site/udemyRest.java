package learn.udemy.site;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.restassures.api.testDataPayloads;

import groovy.lang.GroovyRuntimeException;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class udemyRest {

	public static final String baseURI = "https://rahulshettyacademy.com";

	public static final String mapKey = "qaclick123";

	public static String placeID;

	public static void main(String[] args) {
		udemyRest.addPlace("OK");
		udemyRest.getPlace("29, side layout, cohen 09");
		udemyRest.deletePlace("OK");
		try {
			udemyRest.getPlace("29, side layout, cohen 09");
		} catch (GroovyRuntimeException e) {

			System.out.println("Record not found, Status code: " + e.getLocalizedMessage().split("\n")[1]);

		}

	}

	public static void addPlace(String toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response addPlaceResp = given()
//				.log()
//				.all()
				.queryParam("key", mapKey)
				.header("Content-Type", "application/json")
				.urlEncodingEnabled(false)
				.body(testDataPayloads.addPlaceBody)
				.when()
				.post(testDataPayloads.uriAddPlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		addPlaceResp.then()
//				.log()
//				.all()
				.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.body("status", equalTo(toVerify));

		placeID = addPlaceResp
				.getBody()
				.path("place_id");

		System.out.println("\n\nPlace ID: " + placeID);

		System.out.println("addPlace.asString: " + addPlaceResp.asString());

//		System.out.println("addPlace.statusCode: "+addPlaceResp.statusCode());
	}

	public static void deletePlace(String toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response deletePlaceResp = given()
//			.log()
//			.all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.urlEncodingEnabled(false)
				.body(testDataPayloads.deletePlaceBody.replace("$RunTimeVar1", placeID))
				.when()
				.post(testDataPayloads.uriDeletePlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		System.out.println("deletePlaceResp.asString: " + deletePlaceResp.asString());

		deletePlaceResp.then()
//		.log()
//		.all()
				.assertThat().statusCode(200).body("status", equalTo(toVerify));

		System.out.println("***************Place deleted successfully!***************");

	}

	public static void getPlace(String toVerify) {
		System.out.println(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = baseURI;

		System.out.println("***************Request starts:***************");

		Response getPlaceResp = given()
//			.log()
//			.all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.queryParam("place_id", placeID)
				.urlEncodingEnabled(false)
				.when()
				.get(testDataPayloads.uriGetPlace);

		System.out.println("\n\n***************Response starts***************n\n\n");

		System.out.println("getPlaceResp.asString: " + getPlaceResp.asString());

		getPlaceResp.then()
//		.log()
//		.all()
				.assertThat().statusCode(200).body("address", equalTo(toVerify));
	}

}
