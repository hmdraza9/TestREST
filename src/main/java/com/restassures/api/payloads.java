package com.restassures.api;

public class payloads {

	public static String addPlaceBody = "{\"location\":{\"lat\":-38.383494,\"lng\":33.427362},\"accuracy\":50,\"name\":\"Frontlinea hous\",\"phone_number\":\"(+91) 983 893 3937\",\"address\":\"29, side layout, cohen 09\",\"types\":[\"shoe park\",\"shop\"],\"website\":\"http://google.com\",\"language\":\"French-IN\"}";
	public static String updatePlaceBody = "{\"place_id\":\"$RunTimeVar1\",\"address\":\"$RunTimeVar2\",\"key\":\"$RunTimeVar3\"}";
	public static String deletePlaceBody = "{\"place_id\":\"$RunTimeVar1\"}";
	
	public static final String uriGet = "maps/api/place/get/json";
	
	public static final String uriDelete = "maps/api/place/delete/json";
	
	public static final String uriAdd = "maps/api/place/add/json";
	
	public static final String uriUpdate = "maps/api/place/update/json";

}
