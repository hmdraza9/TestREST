package test.jira.apis;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restassures.api.testDataPayloads;
import com.restassures.utils.UtilMethods;

import groovy.lang.GroovyRuntimeException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class JiraAPIMethods {

	private static final Logger log = LogManager.getLogger(JiraAPIMethods.class);

	public static JiraAPIMethods objJira;

	public static String jiraJSession;

	UtilMethods utils = new UtilMethods();

	testDataPayloads data = new testDataPayloads();
	String response;
	static boolean isCreateIssueSuccess = false;
	static int countAttempted = 0;
	static int countRetryLimit = 999;

	public static void main(String[] args) {

		objJira = new JiraAPIMethods();
		objJira.getJIRAJSession();
		while(!isCreateIssueSuccess) {
			objJira.jiraCreateIssue();
			countAttempted++;
			if(countAttempted>=countRetryLimit)
				isCreateIssueSuccess = true;
		}

	}

	public void jiraCreateIssue() {

		RestAssured.baseURI = data.baseUriJIRA;
		
		RequestSpecification createJIRAIReqSpec = given()
				.log()
				.all()
				.header("Content-Type", "application/json").header("Cookie","JSESSIONID="+jiraJSession)
				.urlEncodingEnabled(false);
		
		Response getJIRAJSessionResp = createJIRAIReqSpec
				.body(data.JIRACreateIssueReqBody)
				.when().post(data.uriJIRACreateIssue);
				
		ValidatableResponse vResp = getJIRAJSessionResp
				.then()
				.log()
				.all()
				.assertThat();
		
		try {
			response = vResp.statusCode(201).extract().asString();
			isCreateIssueSuccess = true;
		} catch (GroovyRuntimeException e) { 
			isCreateIssueSuccess = false;
			
		}
		
	}

	public void getJIRAJSession() {

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification getJIRAJSessionReqSpec = given().log().all().header("Content-Type", "application/json")
				.urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAJSessionReqSpec.body(data.JIRAJSessionReqBody).when()
				.post(data.uriGetJIRAJSession);

		response = getJIRAJSessionResp.then().assertThat().statusCode(200).extract().asString();

		jiraJSession = utils.rawToJson(response).getString("session.value");

		log.info("Jira JSession: >" + jiraJSession + "<");
	}

}
