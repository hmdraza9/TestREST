package test.oauth.REST;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;

public class TestOAuthRestAPI {
	
	public static final String authURL = "https://accounts.google.com/o/oauth2/v2/auth"; //code URL
	
	public static final String accessTokenURL = "https://www.googleapis.com/oauth2/v4/token";
	
	public static final String code = "4%2F0AfJohXna3LaGBuNcTMOVeu4j1wl9IlN7Ch-OB0gmPsFPKF-pmoPTlN6xdvH-nvpd102u8w";
	
	public static final String client_id = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
	
	public static final String client_secret = "erZOWM9g3UtwNRj340YYaK_W";
	
	public static final String redirect_uri = "https://rahulshettyacademy.com/getCourse.php";
	
	public static final String grant_type = "authorization_code";
	
	public static final String scope = "https://www.googleapis.com/auth/userinfo.email";
	
	public static final String auth_url = "https://accounts.google.com/o/oauth2/v2/auth";
	
	public static final String response_type = "code";
	
	UtilMethods utils = new UtilMethods();

	
	public void testOAuthRestAssured() {
		
		RestAssured.baseURI = accessTokenURL;
		
		String response = given()
				.urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", client_id)
		.queryParam("client_secret", client_secret)
		.queryParam("redirect_uri", redirect_uri)
		.queryParam("grant_type", grant_type)
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
//		.log()
//		.all()
//		.post()
//		.then()
//		.log()
//		.all();
		
		response = given().queryParam("access_token", token)
				.when()
				.log()
				.all()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.then()
				.extract()
				.response()
				.asString();
		
		System.out.println("Course response: "+response);
		
	}

/*
 * https://accounts.google.com/o/oauth2/v2/auth
 * ?scope=https://www.googleapis.com/auth/userinfo.email
 * &auth_url=https://accounts.google.com/o/oauth2/v2/auth
 * &client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com
 * &response_type=code
 * &redirect_uri=https://rahulshettyacademy.com/getCourse.php	
 */
	
	public String getOAuthCode() throws IOException {
		
		StringBuilder getCodeURI = new StringBuilder();
		
		getCodeURI
		.append(authURL)
		.append("?")
		.append("scope=").append(scope)
		.append("&auth_url=").append(auth_url)
		.append("&client_id=").append(client_id)
		.append("&response_type=").append(response_type)
		.append("&redirect_uri=").append(redirect_uri);
		
		System.out.println("getCodeURI: "+getCodeURI);
		
		WebDriver driver = new ChromeDriver();
		
		try {
			driver.get(getCodeURI.toString());
			
//			TakesScreenshot scr = (TakesScreenshot) driver;Assert.assertTrue(accessTokenURL, false);
//			File src = scr.getScreenshotAs(OutputType.FILE);
//			FileUtils.copyFile(src, new File("//mlm.png"));
			
			driver.findElement(By.xpath("//input[@aria-label='Email or phone']")).sendKeys("johnedoe070@gmail.com");
			
			driver.findElement(By.xpath("//button/span[text()='Next']")).click();
			
			driver.findElement(By.xpath("//input[@aria-label='Enter your password']")).sendKeys("Test@123");
			
			driver.findElement(By.xpath("//button/span[text()='Next']")).click();
			
			String tempCode = driver.getCurrentUrl();
			
			System.out.println("Temp Code: " +tempCode);
			
			utils.ts(driver);
			
			
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
			utils.ts(driver);
			driver.quit();
		}
		
		return "";
		
	}
}
