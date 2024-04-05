package com.restassured.payloads;

public class URIs {

	// URIs

	public final String uriMapGetPlace = "maps/api/place/get/json";

	public final String uriMapDeletePlace = "maps/api/place/delete/json";

	public final String uriMapAddPlace = "maps/api/place/add/json";

	public final String uriMapUpdatePlace = "maps/api/place/update/json";

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
