package com.restassured.payloads;

import com.restassures.utils.UtilMethods;

public class Payloads {

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

}
