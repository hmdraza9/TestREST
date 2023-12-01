package test.oauth.REST;

import static io.restassured.RestAssured.given;

import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;

public class TestOAuthRestAPI {
	
	public static final String authURL = "https://accounts.google.com/o/oauth2/v2/auth"; //code URL
	
	public static final String accessTokenURL = "https://www.googleapis.com/oauth2/v4/token";
	
	public static final String code = "4%2F0AfJohXneoPF9VEjq7g1r-y5L5vv_fw6t32V9nRQB4WR5akrPlPsaV6sH3iYB3o9Scr438A";
	
	UtilMethods utils = new UtilMethods();

	
	public void testOAuthRestAssured() {
		
		RestAssured.baseURI = accessTokenURL;
		
		String response = given()
				.urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when()
		.log()
		.all()
		.post(accessTokenURL)
		.then()
		.log()
		.all()
		.extract()
		.response()
		.asString();
		
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

	public static String getOAuthCode() {
		
		WebDriver driver = new ChromeDriver();
		
		
		return "";
		
	}
}
