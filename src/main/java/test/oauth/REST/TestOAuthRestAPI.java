package test.oauth.REST;

import static io.restassured.RestAssured.given;

import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;

public class TestOAuthRestAPI {
	
	public static final String authURL = "https://accounts.google.com/o/oauth2/v2/auth";
	
	public static final String accessTokenURL = "https://www.googleapis.com/oauth2/v4/token";
	
	UtilMethods utils = new UtilMethods();

	
	public void testOAuthRestAssured() {
		
		RestAssured.baseURI = accessTokenURL;
		
		String response = given()
		.queryParam("code", "4%2F0AfJohXkt2s3zr7Olg9hBZoXxDOywtbk5fjxdv_06VYSTG3CV6pT6aSUydpw_Ih8Vqm86xQ")
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when()
		.log()
		.all()
		.post(accessTokenURL).asString();
		
		String token = utils.rawToJson(response).getString("access_token");
		
		System.out.println("Token: "+token);
		
		
		
		
		
//		given()
//		.urlEncodingEnabled(false)
//		.queryParam("scope", "https://www.googleapis.com/auth/userinfo.email")
//		.queryParam("auth_url", "https://accounts.google.com/o/oauth2/v2/auth")
//		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
//		.queryParam("response_type", "code")
//		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
//		.when()
////		.log()
////		.all()
//		.post()
//		.then()
//		.log()
//		.all();
		
	}

}
