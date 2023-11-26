package test.jira.apis;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restassures.api.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JiraAPIMethods {

	private static final Logger log = LogManager.getLogger(JiraAPIMethods.class);

	public static JiraAPIMethods objJira;
	
	public static String jiraIssue = "";
	
	public static String jiraIssueID = "";
	
	public static String jiraIssueCommentID = "";

	public static String jiraJSession;

	UtilMethods utils = new UtilMethods();

	testDataPayloads data = new testDataPayloads();
	String response;
	static boolean isCreateIssueSuccess = false;
	static int countAttempted = 0;
	static int countRetryLimit = 1;

	public static void main(String[] args) {
		
		objJira = new JiraAPIMethods();
		objJira.getJIRAJSession();
		objJira.jiraCreateIssue();
		objJira.jiraGetIssue();
		objJira.jiraDeleteIssue();
		objJira.jiraCreateIssue();
		objJira.jiraCommentIssue();
		objJira.jiraUpdateCommentIssue();
		
//		while (!isCreateIssueSuccess) {
//			objJira.getJIRAJSession();
//			objJira.jiraCreateIssue();
//			countAttempted++;
//			if (countAttempted >= countRetryLimit) {
//				isCreateIssueSuccess = true;
//			}
//			log.info("isCreateIssueSuccess: " + isCreateIssueSuccess);
//			log.info("countRetryLimit: " + countRetryLimit);
//			log.info("countAttempted: " + countAttempted);
//		}

	}

	
	public void getJIRAJSession() {
		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification getJIRAJSessionReqSpec = given().log().all().header("Content-Type", "application/json")
				.urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAJSessionReqSpec.body(data.JIRAJSessionReqBody).when()
				.post(data.uriGetJIRAJSession);

		response = getJIRAJSessionResp.then().assertThat().statusCode(200).extract().asString();

		jiraJSession = utils.rawToJson(response).getString("session.value");

		log.info("Jira JSession: >" + jiraJSession + "<");
	}

	
	public void jiraCreateIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification createJIRAIReqSpec = given().log().all().header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession).urlEncodingEnabled(false);

		Response getJIRAJSessionResp = createJIRAIReqSpec.body(data.JIRACreateIssueTaskReqBody).when()
				.post(data.uriJIRACreateIssue);

		response = getJIRAJSessionResp.then().log().all().assertThat().statusCode(201).extract().asString();
		
		jiraIssue = utils.rawToJson(response).getString("key");
		
		jiraIssueID = utils.rawToJson(response).getString("id");
		
		log.info("Create issue response - "+response+",\ncreated issue: "+jiraIssue);

//		try {
//			response = vResp.extract().asString();
//			isCreateIssueSuccess = true;
//		} catch (GroovyRuntimeException e) {
//			isCreateIssueSuccess = false;
//
//		}

	}


	public void jiraGetIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());
		
		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification getJIRAReqSpec = given().log().all().header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession).urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAReqSpec.when()
				.get(data.uriJIRAGetIssue+jiraIssue);

		response = getJIRAJSessionResp.then().log().all().assertThat().statusCode(200).extract().asString();
		
		log.info("Get issue response - "+response);

//		try {
//			response = vResp.extract().asString();
//			isCreateIssueSuccess = true;
//		} catch (GroovyRuntimeException e) {
//			isCreateIssueSuccess = false;
//
//		}

	}


	public void jiraDeleteIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification deleteJIRAReqSpec = given().log().all().header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession).urlEncodingEnabled(false);

		Response deleteJIRAJSessionResp = deleteJIRAReqSpec.body(data.JIRACreateIssueTaskReqBody).when()
				.delete(data.uriJIRADeleteIssue.replace("#RunTimeVar", jiraIssue));

		response = deleteJIRAJSessionResp.then().log().all().assertThat().statusCode(204).extract().asString();
		
		log.info("Delete issue response - "+response);

//		try {
//			response = vResp.extract().asString();
//			isCreateIssueSuccess = true;
//		} catch (GroovyRuntimeException e) {
//			isCreateIssueSuccess = false;
//
//		}

	}


	public void jiraCommentIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification commentJIRAReqSpec = given().log().all().header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession).urlEncodingEnabled(false);

		Response commentJIRAResp = commentJIRAReqSpec.body(data.commentIssueReqBody).when()
				.post(data.uriJIRACommentIssue.replace("#RunTimeVar", jiraIssue));

		response = commentJIRAResp.then().log().all().assertThat().statusCode(201).extract().asString();
		
		log.info("Comment issue response - "+response);
		
		jiraIssueCommentID = utils.rawToJson(response).getString("id");

//		try {
//			response = vResp.extract().asString();
//			isCreateIssueSuccess = true;
//		} catch (GroovyRuntimeException e) {
//			isCreateIssueSuccess = false;
//
//		}

	}

	
	public void jiraUpdateCommentIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification commentJIRAReqSpec = given().log().all().header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession).urlEncodingEnabled(false);

		Response commentJIRAResp = commentJIRAReqSpec.body(data.commentUpdateIssueReqBody).when()
				.put(data.uriJIRACommentUpdateIssue.replace("#issueID", jiraIssue).replace("#commentID", jiraIssueCommentID));

		response = commentJIRAResp.then().log().all().assertThat().statusCode(200).extract().asString();
		
		log.info("Comment issue response - "+response);

//		try {
//			response = vResp.extract().asString();
//			isCreateIssueSuccess = true;
//		} catch (GroovyRuntimeException e) {
//			isCreateIssueSuccess = false;
//
//		}

	}


}
