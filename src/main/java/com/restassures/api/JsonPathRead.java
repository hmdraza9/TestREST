package com.restassures.api;

import io.restassured.RestAssured;
import org.json.JSONObject;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonPathRead {

	public static void main(java.lang.String[] args) {
		// TODO Auto-generated method stub

		String jsonText = "{\"cod\":401, \"message\": \"Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.\"}";

//		System.out.println("jsonText: \n"+jsonText);

		Response response = RestAssured.given().get(
				"http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02");

		System.out.println("Call success?:  " + (response.statusCode() == 200) + ";\n" + "Status Code is: "
				+ response.statusCode());

		JsonPath jpath = response.jsonPath();

		System.out.println("coord.lon:      " + jpath.get("coord.lon").toString());

		System.out.println("coord.lat:      " + jpath.get("coord.lat").toString());

		System.out.println("Main:           " + jpath.get("weather[0].main").toString());

		System.out.println("Description:    " + jpath.get("weather[0].description").toString());

		System.out.println("Temperature:    " + jpath.get("main.temp").toString());

		System.out.println("Pressure:       " + jpath.get("main.pressure").toString());

		System.out.println("Humidity:       " + jpath.get("main.humidity").toString());

		System.out.println("Visibility:     " + jpath.get("visibility").toString());

		int visi = Integer.parseInt(jpath.get("visibility").toString());

		if (visi > 9000) {
			System.out.println("************ATC Ok************");
		} else {
			System.out.println("************ATC NOT Ok************");

			// https://samples.openweathermap.org/

		}

	}

}
