package test.jira.apis;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restassures.api.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JiraAPIMethods {

	private static final Logger log = LogManager.getLogger(JiraAPIMethods.class);

	public static JiraAPIMethods objJira;
	
	SessionFilter session = new SessionFilter();

	public static String jiraIssue = "";

	public static String jiraIssueID = "";

	public static String jiraIssueCommentID = "";

	public static String jiraJSession;

	UtilMethods utils = new UtilMethods();

	testDataPayloads data = new testDataPayloads();
	String response;
	static boolean isCreateIssueSuccess = false;
	
	
	
	
	

	public static void main(String[] args) throws InterruptedException, IOException {
		
		objJira = new JiraAPIMethods();
		
//		objJira.sessionFilterExampleJIRA();
				
		objJira.getJIRAJSession();
		objJira.jiraCreateIssue();
//		Thread.sleep(5000);
//		objJira.jiraGetIssue();
//		objJira.jiraDeleteIssue();//using path parameter to make URI dynamic
		objJira.jiraCreateIssue();
//		objJira.jiraCommentIssue();
//		objJira.jiraUpdateCommentIssue();
		objJira.jiraAttachToIssue();

	}
	
	
	
	
	public void jiraAttachToIssue() {
		String filePath = "src/test/resources/JIRAFiles/error.txt";
		File file = new File(filePath);
//		file.renameTo(new File(filePath.replace(file.getName(), UtilMethods.getTime())));
		
		RestAssured.baseURI = data.baseUriJIRA;
		RequestSpecification attachJIRAReqSpec = given()
				.filter(session)
				.header("X-Atlassian-Token","no-check")
				.header("Content-Type","multipart/form-data")
				.pathParam("issueIdOrKey",jiraIssue)
				.urlEncodingEnabled(false).log().all();
		
		
		Response attachJIRAResp = attachJIRAReqSpec
				.multiPart("file",file)
				.filter(session)
				.when()
				.post(data.uriJIRAIssueAttachment);
		
		String response = attachJIRAResp
				.then()
				.log()
				.all()
				.assertThat()
				.statusCode(200)
				.extract()
				.asString();
		
		log.info("File attached to issue: "+jiraIssue+"; file ID: "+utils.rawToJson(response).getString("id"));
	}
	
	
	public void sessionFilterExampleJIRA() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification getJIRAJSessionReqSpec = given()
				// .log()
				// .all()
				.filter(session)
				.header("Content-Type", "application/json").urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAJSessionReqSpec.body(data.JIRAJSessionReqBody)
				.when()
				.post(data.uriGetJIRAJSession);
				
		getJIRAJSessionReqSpec = given()
				// .log()
				// .all()
				.header("Content-Type", "application/json")
				.urlEncodingEnabled(false);

		getJIRAJSessionResp = getJIRAJSessionReqSpec.body(data.JIRACreateIssueTaskReqBody)
				.filter(session)
				.when()
				.post(data.uriJIRACreateIssue);

		response = getJIRAJSessionResp.then()
				// .log()
				// .all()
				.assertThat()
				.statusCode(201)
				.extract()
				.asString();

		jiraIssue = utils.rawToJson(response).getString("key");

		jiraIssueID = utils.rawToJson(response).getString("id");

		log.info("Create issue response - " + response);
		log.info("created issue: " + jiraIssue);
	}

	public void getJIRAJSession() {
		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification getJIRAJSessionReqSpec = given()
				// .log()
				// .all()
				.filter(session)
				.header("Content-Type", "application/json")
				.urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAJSessionReqSpec
				.body(data.JIRAJSessionReqBody)
				.when()
				.post(data.uriGetJIRAJSession);

		response = getJIRAJSessionResp
				.then()
				.assertThat()
				.statusCode(200)
				.extract()
				.asString();

		jiraJSession = utils.rawToJson(response).getString("session.value");

		log.info("Jira JSession: >" + jiraJSession + "<");
	}

	public void jiraCreateIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification createJIRAIReqSpec = given()
				// .log()
				// .all()
				.header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response getJIRAJSessionResp = createJIRAIReqSpec.body(data.JIRACreateIssueTaskReqBody)
				.when()
				.post(data.uriJIRACreateIssue);

		response = getJIRAJSessionResp.then()
				// .log()
				// .all()
				.assertThat()
				.statusCode(201)
				.extract()
				.asString();

		jiraIssue = utils.rawToJson(response).getString("key");

		jiraIssueID = utils.rawToJson(response).getString("id");

		log.info("Create issue response - " + response);
		log.info("created issue: " + jiraIssue);

	}

	public void jiraGetIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification getJIRAReqSpec = given()
				// .log()
				// .all()
				.header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAReqSpec
				.when()
				.get(data.uriJIRAGetIssue + jiraIssue);

		response = getJIRAJSessionResp.then()
				// .log()
				// .all()
				.assertThat()
				.statusCode(200)
				.extract()
				.asString();

		log.info("Get issue response - " + response);

	}

	public void jiraDeleteIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification deleteJIRAReqSpec = given()
				.pathParam("key", jiraIssue)
//				 .log()
//				 .all()
				.header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response deleteJIRAJSessionResp = deleteJIRAReqSpec
				.body(data.JIRACreateIssueTaskReqBody)
				.when()
				.delete(data.uriJIRADeleteIssue);

		response = deleteJIRAJSessionResp.then()
//				 .log()
//				 .all()
				.assertThat()
				.statusCode(204)
				.extract()
				.asString();

		log.info("Delete issue response - " + response);

	}

	public void jiraCommentIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification commentJIRAReqSpec = given()
				.pathParam("key", jiraIssue)
//				 .log()
//				 .all()
				.header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response commentJIRAResp = commentJIRAReqSpec.body(data.commentIssueReqBody)
				.when()
				.post(data.uriJIRACommentIssue);

		response = commentJIRAResp.then()
//				 .log()
//				 .all()
				.assertThat()
				.statusCode(201)
				.extract()
				.asString();

		log.info("Comment issue response - " + response);

		jiraIssueCommentID = utils.rawToJson(response)
				.getString("id");

	}

	public void jiraUpdateCommentIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = data.baseUriJIRA;

		RequestSpecification commentJIRAReqSpec = given()
				.pathParam("issueID", jiraIssue)
				.pathParam("commentID", jiraIssueCommentID)
//				 .log()
//				 .all()
				.header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response commentJIRAResp = commentJIRAReqSpec.body(data.commentUpdateIssueReqBody)
				.when()
				.put(data.uriJIRACommentUpdateIssue);

		response = commentJIRAResp.then()
//				 .log()
//				 .all()
				.assertThat()
				.statusCode(200)
				.extract()
				.asString();

		log.info("Comment issue response - " + response);

	}

}
