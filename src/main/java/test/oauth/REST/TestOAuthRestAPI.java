package test.oauth.REST;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import test.pojo.clsses.CoursesMain;

public class TestOAuthRestAPI {
	
	public static final String authURL = "https://accounts.google.com/o/oauth2/v2/auth"; //code URL
	
	public static final String accessTokenURL = "https://www.googleapis.com/oauth2/v4/token";
	
	public static final String code = "4%2F0AfJohXl1NXktOGbzFOnSzbBf3kOm2RnEpaNm_9e3zDdUfGsMcPYSxQfmKBzSwXkzsKoU7g";
	
	public static final String client_id = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
	
	public static final String client_secret = "erZOWM9g3UtwNRj340YYaK_W";
	
	public static final String redirect_uri = "https://rahulshettyacademy.com/getCourse.php";
	
	public static final String grant_type = "authorization_code";
	
	public static final String scope = "https://www.googleapis.com/auth/userinfo.email";
	
	public static final String auth_url = "https://accounts.google.com/o/oauth2/v2/auth";
	
	public static final String response_type = "code";
	
	UtilMethods utils = new UtilMethods();

	
	public void getCourses() {
		
		RestAssured.baseURI = accessTokenURL;
		
		Response response = given()
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
		.response();
		
		String token = utils.rawToJson(response.asString()).getString("access_token");
		
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
		
		
		// Getting courses here
		
		response = 
		given()
		.queryParam("access_token", token)
		.expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php");
		
		CoursesMain cs = response.as(CoursesMain.class);

		System.out.println("Total WebAutomation courses: "+cs.getCourses().getWebAutomation().size());
		System.out.println("Total API courses: "+cs.getCourses().getApi().size());
		System.out.println("Total Mobile courses: "+cs.getCourses().getMobile().size());
		
		System.out.println("Get Instructor: "+cs.getInstructor());
		System.out.println("Get URL: "+cs.getUrl());
		System.out.println("Get Services: "+cs.getServices());
		System.out.println("Get Expertise: "+cs.getExpertise());
		System.out.println("Get Course Mobile 0 Title: "+cs.getCourses().getMobile().get(0).getCourseTitle());
		System.out.println("Get Course Mobile 0 Price: "+cs.getCourses().getMobile().get(0).getPrice());
		System.out.println("Get Course API 1 Title: "+cs.getCourses().getApi().get(1).getCourseTitle());
		System.out.println("Get Course API 1 Price: "+cs.getCourses().getApi().get(1).getPrice());
		
		System.out.println("Course response: "+response.asString());
		
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
