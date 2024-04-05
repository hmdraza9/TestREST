package com.restassured.payloads;

import com.restassures.utils.UtilMethods;

public class testDataPayloads {

	/*
	 * REQUEST BODY
	 */

	public static String addPlaceBodyPlaceholder = "{\"location\":{\"lat\":-38.383494,\"lng\":33.427362},\"accuracy\":50,\"name\":\"Frontlinea hous\",\"phone_number\":\"(+91) 983 893 3937\",\"address\":\"#address#, side layout, cohen 09\",\"types\":[\"shoe park\",\"shop\"],\"website\":\"http://google.com\",\"language\":\"French-IN\"}";
	public static String addPlaceBody = "{\"location\":{\"lat\":-38.383494,\"lng\":33.427362},\"accuracy\":50,\"name\":\"Frontlinea hous\",\"phone_number\":\"(+91) 983 893 3937\",\"address\":\"999, side layout, cohen 09\",\"types\":[\"shoe park\",\"shop\"],\"website\":\"http://google.com\",\"language\":\"French-IN\"}";
	public static String updatePlaceBody = "{\"place_id\":\"$RunTimeVar1\",\"address\":\"$RunTimeVar2\",\"key\":\"$RunTimeVar3\"}";
	public static String deletePlaceBody = "{\"place_id\":\"$RunTimeVar1\"}";
	public String courseBody = "{\r\n" + "  \"dashboard\": {\r\n" + "    \"purchaseAmount\": 1162,\r\n"
			+ "    \"website\": \"rahulshettyacademy.com\"\r\n" + "  },\r\n" + "  \"courses\": [\r\n" + "    {\r\n"
			+ "      \"title\": \"Selenium Python\",\r\n" + "      \"price\": 50,\r\n" + "      \"copies\": 6\r\n"
			+ "    },\r\n" + "    {\r\n" + "      \"title\": \"Cypress\",\r\n" + "      \"price\": 40,\r\n"
			+ "      \"copies\": 4\r\n" + "    },\r\n" + "    {\r\n" + "      \"title\": \"RPA\",\r\n"
			+ "      \"price\": 45,\r\n" + "      \"copies\": 10\r\n" + "    },\r\n" + "     {\r\n"
			+ "      \"title\": \"Appium\",\r\n" + "      \"price\": 36,\r\n" + "      \"copies\": 7\r\n" + "    }\r\n"
			+ "    \r\n" + "    \r\n" + "    \r\n" + "  ]\r\n" + "}\r\n" + "";

	public String JIRAJSessionReqBody = "{\"username\":\"hmdraza9\",\"password\":\"@fFe3m4R7364X$Y\"}";

	public String JIRACreateIssueTaskReqBody = "{\r\n" + "    \"fields\": {\r\n" + "        \"project\": {\r\n"
			+ "            \"key\": \"AHR\"\r\n" + "        },\r\n" + "        \"summary\": \"New Issues for - "
			+ UtilMethods.getTime() + "\",\r\n" + "        \"description\": \"New task to modify logo\",\r\n"
			+ "        \"issuetype\": {\r\n" + "            \"name\": \"Task\"\r\n" + "        }\r\n" + "    }\r\n"
			+ "}";

	public String commentIssueReqBody = "{\r\n" + "\r\n" + "	\"body\": \"This is comment - " + UtilMethods.getTime()
			+ "\",\r\n" + "	\"visibility\":{\r\n" + "	\r\n" + "		\"type\":\"role\",\r\n"
			+ "		\"value\":\"Administrators\"\r\n" + "	\r\n" + "	}\r\n" + "\r\n" + "}";

	public String commentUpdateIssueReqBody = "{\r\n" + "\r\n" + "	\"body\": \"This is comment update - "
			+ UtilMethods.getTime() + "\",\r\n" + "	\"visibility\":{\r\n" + "	\r\n" + "		\"type\":\"role\",\r\n"
			+ "		\"value\":\"Administrators\"\r\n" + "	\r\n" + "	}\r\n" + "\r\n" + "}";

	public String petStoreCreatePetBody = "{\r\n" + "  \"category\": {\r\n" + "    \"id\": 1,\r\n"
			+ "    \"name\": \"dog\"\r\n" + "  },\r\n" + "  \"name\": \"$petName\",\r\n" + "  \"photoUrls\": [\r\n"
			+ "    \"string\"\r\n" + "  ],\r\n" + "  \"tags\": [\r\n" + "    {\r\n" + "      \"id\": 0,\r\n"
			+ "      \"name\": \"string\"\r\n" + "    }\r\n" + "  ],\r\n" + "  \"status\": \"pending\"\r\n" + "}";

	// URIs

	public static final String uriMapGetPlace = "maps/api/place/get/json";

	public static final String uriMapDeletePlace = "maps/api/place/delete/json";

	public static final String uriMapAddPlace = "maps/api/place/add/json";

	public static final String uriMapUpdatePlace = "maps/api/place/update/json";

	public final String uriJIRABase = "http://localhost:8080";

	public final String uriJIRAGetJSession = "rest/auth/1/session";

	public final String uriJIRACreateIssue = "rest/api/2/issue";

	public final String uriJIRAGetIssue = "rest/api/2/issue/{key}";

	public final String uriJIRADeleteIssue = "rest/api/2/issue/{key}";

	public final String uriJIRACommentIssue = "rest/api/2/issue/{key}/comment";

	public final String uriJIRACommentUpdateIssue = "rest/api/2/issue/{issueID}/comment/{commentID}";

	public final String uriJIRAIssueAttachment = "/rest/api/2/issue/{issueIdOrKey}/attachments";

	public final String urlEcomAuth = "https://rahulshettyacademy.com/api/ecom/auth/login";

	public final String urlEcomAdToCart = "https://rahulshettyacademy.com/api/ecom/user/add-to-cart";

	public final String urlEcomGetAllProducts = "https://rahulshettyacademy.com/api/ecom/product/get-all-products";

	public final String urlpetStoreBaseURL = "https://petstore.swagger.io";

	public final String urltypiCodeBaseURL = "https://jsonplaceholder.typicode.com";

	public final String urlepamEvenetApiURL = "https://events.epam.com/api/v2/events";

	public final String urlopenWeatherApiURL = "http://api.openweathermap.org";

	public final String uriPetStoreCreate = "/v2/pet";

	public final String uriPetStoreGet = "/v2/pet/{petID}";

	public final String uriTypiCodeUsers = "/users";

	public final String uriOpenWeather = "/data/2.5/weather";
}
