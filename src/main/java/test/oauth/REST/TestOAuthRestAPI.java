package test.oauth.REST;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import test.pojo.courses.CoursesMain;

public class TestOAuthRestAPI {
	
	private static final Logger log = LogManager.getLogger(TestOAuthRestAPI.class);
	
	private String[] coursesExpArr = {"Selenium Webdriver Java","Cypress","Protractor"};
	
	public static final String authURL = "https://accounts.google.com/o/oauth2/v2/auth"; //code URL
	
	public static final String accessTokenURL = "https://www.googleapis.com/oauth2/v4/token";
	
	public static String code = "4%2F0AfJohXn7LCXlpZGgG9vHaGh5hBb6b7Z3OIfXPfPd2lq1C7p7uVQcEy_B6t9JFtYx1fmMkQ";
	
	public static final String client_id = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
	
	public static final String client_secret = "erZOWM9g3UtwNRj340YYaK_W";
	
	public static final String redirect_uri = "https://rahulshettyacademy.com/getCourse.php";
	
	public static final String grant_type = "authorization_code";
	
	public static final String scope = "https://www.googleapis.com/auth/userinfo.email";
	
	public static final String auth_url = "https://accounts.google.com/o/oauth2/v2/auth";
	
	public static final String response_type = "code";
	
	UtilMethods utils = new UtilMethods();

	
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
			
			log.info("getCodeURI: "+getCodeURI);
			
			WebDriver driver = new ChromeDriver();
			
	//		WebDriver driver = new FirefoxDriver();
			
	//		WebDriver driver = new HtmlUnitDriver();
			
			try {
				driver.get(getCodeURI.toString());
				
	//			TakesScreenshot scr = (TakesScreenshot) driver;Assert.assertTrue(accessTokenURL, false);
	//			File src = scr.getScreenshotAs(OutputType.FILE);
	//			FileUtils.copyFile(src, new File("//mlm.png"));
				
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
				driver.findElement(By.xpath("//input[@aria-label='Email or phone']")).sendKeys("johnedoe070@gmail.com");
				
				driver.findElement(By.xpath("//button/span[text()='Next']")).click();
				
				driver.findElement(By.xpath("//input[@aria-label='Enter your password']")).sendKeys("Test@123");
				
				driver.findElement(By.xpath("//button/span[text()='Next']")).click();
				
				String tempCode = driver.getCurrentUrl();
				
				log.info("Temp Code: " +tempCode);
				
				utils.ts(driver);
				
				
				driver.quit();
			} catch (Exception e) {
				e.printStackTrace();
				utils.ts(driver);
				driver.quit();
			}
			
			return "";
			
		}

	public void getCourses() {
		
		RestAssured.baseURI = accessTokenURL;
		code = "4%2F0AfJohXl3NC28WLnGEZ83nkvqTrOzMKEbRE1CVmDHKeo5z8JTkLC5uEx6l1W4S-DGJldL0g";
		
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
		
		log.info("Token: "+token);
		
		
		
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
		
		log.info("Course API response: "+response.asString());

		log.info("Total WebAutomation courses: "+cs.getCourses().getWebAutomation().size());
		log.info("Total API courses: "+cs.getCourses().getApi().size());
		log.info("Total Mobile courses: "+cs.getCourses().getMobile().size());
		
		log.info("Get Instructor: "+cs.getInstructor());
		log.info("Get URL: "+cs.getUrl());
		log.info("Get Services: "+cs.getServices());
		log.info("Get Expertise: "+cs.getExpertise());
		log.info("Get Course Mobile 0 Title: "+cs.getCourses().getMobile().get(0).getCourseTitle());
		log.info("Get Course Mobile 0 Price: "+cs.getCourses().getMobile().get(0).getPrice());
		log.info("Get Course API 1 Title: "+cs.getCourses().getApi().get(1).getCourseTitle());
		log.info("Get Course API 1 Price: "+cs.getCourses().getApi().get(1).getPrice());
		
		List<String> coursesAct = new ArrayList<>();
		List<String> coursesExp = Arrays.asList(coursesExpArr);
		
		for(int i =0;i< cs.getCourses().getWebAutomation().size();i++) {
			String cTitle = cs.getCourses().getWebAutomation().get(i).getCourseTitle();
			coursesAct.add(cTitle);
			log.info("Added to actual course list: "+cTitle);
			System.out.println("Web Automation course title: ["+i+"] "+cTitle);
			
		}
		
		log.info("Is course expected equal to actual?: "+coursesAct.equals(coursesExp));
		Assert.assertTrue(coursesAct.equals(coursesExp));
		
	}
}
