package test.jira.apis;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.restassured.payloads.Payloads;
import com.restassured.payloads.HTTPCode;
import com.restassured.payloads.URIs;
import com.restassured.payloads.URLs;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JiraAPIMethods {

	private static final Logger log = LogManager.getLogger(JiraAPIMethods.class);

	public static final String homePath = "src/test/resources/JIRAFiles/";

	SessionFilter session = new SessionFilter();

	public static String jiraIssue = "";

	public static String jiraIssueID = "";

	public static String jiraIssueCommentID = "";

	public static String jiraIssueExpCommentID = "10022";

	public static String jiraJSession;

	UtilMethods utils = new UtilMethods();

	Payloads objPayLoad = new Payloads();

	URIs objURI = new URIs();

	URLs objURL = new URLs();

	public static JiraAPIMethods objJira = new JiraAPIMethods();

	String response;

	static boolean isCreateIssueSuccess = false;

	// ##########################____MAIN____##########################

	public static void main(String[] args) throws InterruptedException, IOException {

		objJira.sessionFilterExampleJIRA();
		objJira.getJIRAJSession(); // this have to be uncommented
		objJira.jiraCreateIssue();
		objJira.jiraCommentIssue();
		Thread.sleep(5000);
		objJira.jiraGetIssue();
		objJira.filterContentVerifyData();
		objJira.jiraDeleteIssue();
		objJira.jiraCreateIssue();
		objJira.jiraCommentIssue();
		objJira.jiraUpdateCommentIssue();
		objJira.jiraAttachToIssue();

	}

	public static File getFileToJIRA() throws IOException, InterruptedException {

		log.info("Home Path: " + homePath);

//		File srcFile = new File(homePath + "AttachmentJIRA.txt");
		File srcFile = new File("logs/logs.log");
		File destFile = new File(
				homePath + "/Archive/" + UtilMethods.getTime() + "." + FilenameUtils.getExtension(srcFile.getPath()));

		log.info("Destination file: " + destFile.getAbsolutePath());
		FileUtils.copyFile(srcFile, destFile);
		Thread.sleep(5000);
//		FileUtils.delete(destFile);
		Assert.assertTrue(destFile.exists());
		return destFile;

	}

	public void jiraAttachToIssue() throws IOException, InterruptedException {

		File file = JiraAPIMethods.getFileToJIRA();
		RestAssured.baseURI = objURI.uriJIRABase;
		RequestSpecification attachJIRAReqSpec = given().filter(session).header("X-Atlassian-Token", "no-check")
				.header("Content-Type", "multipart/form-objPayLoad").pathParam("issueIdOrKey", jiraIssue)
				.urlEncodingEnabled(false).log().all();

		Response attachJIRAResp = attachJIRAReqSpec.multiPart("file", file).filter(session).when()
				.post(objURI.uriJIRAIssueAttachment);

		String response = attachJIRAResp.then().log().all().assertThat().statusCode(HTTPCode.OK200).extract()
				.asString();

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

		log.info("File attached to issue: " + jiraIssue + "; file ID: " + utils.rawToJson(response).getString("id")
				+ " and file size: " + (file.length() / 1000) + " KB");
	}

	public void sessionFilterExampleJIRA() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification getJIRAJSessionReqSpec = given()
				// .log()
				// .all()
				.filter(session).header("Content-Type", "application/json").urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAJSessionReqSpec.body(objPayLoad.JIRAJSessionReqBody).when()
				.post(objURI.uriJIRAGetJSession);

		getJIRAJSessionReqSpec = given()
				// .log()
				// .all()
				.header("Content-Type", "application/json").urlEncodingEnabled(false);

		getJIRAJSessionResp = getJIRAJSessionReqSpec.body(objPayLoad.JIRACreateIssueTaskReqBody).filter(session).when()
				.post(objURI.uriJIRACreateIssue);

		response = getJIRAJSessionResp.then()
				// .log()
				// .all()
				.assertThat().statusCode(HTTPCode.OK201).extract().asString();

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

		jiraIssue = utils.rawToJson(response).getString("key");

		jiraIssueID = utils.rawToJson(response).getString("id");

		log.info("created issue: " + jiraIssue);
	}

	public void getJIRAJSession() {
		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification getJIRAJSessionReqSpec = given().relaxedHTTPSValidation()
				// .log()
				// .all()
				.filter(session).header("Content-Type", "application/json").urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAJSessionReqSpec.body(objPayLoad.JIRAJSessionReqBody).when()
				.post(objURI.uriJIRAGetJSession);

		response = getJIRAJSessionResp.then().assertThat().statusCode(HTTPCode.OK200).extract().asString();

		jiraJSession = utils.rawToJson(response).getString("session.value");

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

		log.info("Jira JSession: >" + jiraJSession + "<");
	}

	public void jiraCreateIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification createJIRAIReqSpec = given()
				// .log()
				// .all()
				.header("Content-Type", "application/json").filter(session)
//				.header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response getJIRAJSessionResp = createJIRAIReqSpec.body(objPayLoad.JIRACreateIssueTaskReqBody).when()
				.post(objURI.uriJIRACreateIssue);

		response = getJIRAJSessionResp.then()
				// .log()
				// .all()
				.assertThat().statusCode(HTTPCode.OK201).extract().asString();

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

		jiraIssue = utils.rawToJson(response).getString("key");

		jiraIssueID = utils.rawToJson(response).getString("id");

		log.info("created issue: " + jiraIssue);

	}

	public void jiraGetIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification getJIRAReqSpec = given().log().all().filter(session)
				.header("Content-Type", "application/json").urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAReqSpec.when().pathParam("key", jiraIssue).get(objURI.uriJIRAGetIssue);

		response = getJIRAJSessionResp.then().log().all().assertThat().statusCode(HTTPCode.OK200).extract()
				.asString();

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

	}

	public void filterContentVerifyData() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification getJIRAReqSpec = given().log().all().filter(session)
				.header("Content-Type", "application/json").queryParam("fields", "comment").urlEncodingEnabled(false);

		Response getJIRAJSessionResp = getJIRAReqSpec.when().pathParam("key", "AHR-5").get(objURI.uriJIRAGetIssue);

		response = getJIRAJSessionResp.then()
//					 .log()
//					 .all()
				.assertThat().statusCode(HTTPCode.OK200).extract().asString();

		int commentSize = utils.rawToJson(response).getInt("fields.comment.comments.size()");
		boolean isCommFound = false;
		if (commentSize > 0) {
			for (int i = 0; i < commentSize; i++) {

				int commentIDAct = utils.rawToJson(response).getInt("fields.comment.comments[" + i + "].id");
//					log.info("comment-matches??###### = "+(commentIDTemp == Integer.parseInt(jiraIssueCommentID)));

				if (commentIDAct == Integer.parseInt(jiraIssueExpCommentID)) {
					log.info("Comment ID: " + commentIDAct + " ; "
							+ utils.rawToJson(response).getString("fields.comment.comments[" + i + "]"));
					log.info("Comment Body: '"
							+ utils.rawToJson(response).getString("fields.comment.comments[" + i + "].body") + "'");
					isCommFound = true;
					log.error("Comment found? - " + isCommFound);
					log.error("Total comments: " + commentSize);

					break;
				}
			}
		} else {
			log.error("No comments associated with this issue: " + jiraIssue);
		}

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

	}

	public void jiraDeleteIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification deleteJIRAReqSpec = given().pathParam("key", jiraIssue)
//				 .log()
//				 .all()
				.header("Content-Type", "application/json").header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response deleteJIRAJSessionResp = deleteJIRAReqSpec.body(objPayLoad.JIRACreateIssueTaskReqBody).when()
				.delete(objURI.uriJIRADeleteIssue);

		response = deleteJIRAJSessionResp.then()
//				 .log()
//				 .all()
				.assertThat().statusCode(HTTPCode.OK204).extract().asString();

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

	}

	public void jiraCommentIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification commentJIRAReqSpec = given().pathParam("key", jiraIssue)
//				 .log()
//				 .all()
				.header("Content-Type", "application/json").header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response commentJIRAResp = commentJIRAReqSpec.body(objPayLoad.commentIssueReqBody).when()
				.post(objURI.uriJIRACommentIssue);

		response = commentJIRAResp.then()
//				 .log()
//				 .all()
				.assertThat().statusCode(HTTPCode.OK201).extract().asString();

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

		jiraIssueCommentID = utils.rawToJson(response).getString("id");

	}

	public void jiraUpdateCommentIssue() {

		log.info(new Throwable().getStackTrace()[0].getMethodName());

		RestAssured.baseURI = objURI.uriJIRABase;

		RequestSpecification commentJIRAReqSpec = given().pathParam("issueID", jiraIssue)
				.pathParam("commentID", jiraIssueCommentID)
//				 .log()
//				 .all()
				.header("Content-Type", "application/json").header("Cookie", "JSESSIONID=" + jiraJSession)
				.urlEncodingEnabled(false);

		Response commentJIRAResp = commentJIRAReqSpec.body(objPayLoad.commentUpdateIssueReqBody).when()
				.put(objURI.uriJIRACommentUpdateIssue);

		response = commentJIRAResp.then()
//				 .log()
//				 .all()
				.assertThat().statusCode(HTTPCode.OK200).extract().asString();

		log.info("Response: \n@@@@@@@@@@@@@@\n" + response + "\n@@@@@@@@@@@@@@\n");

	}

}
