package test.pojo.courses;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import test.oauth.REST.TestOAuthRestAPI;

public class Courses {

	private static final Logger log = LogManager.getLogger(Courses.class);

	private List<WebAutomation> webAutomation;
	private List<API> api;
	private List<Mobile> mobile;

	public List<WebAutomation> getWebAutomation() {
		log.info("Returned webAutomation list: " + webAutomation);
		return webAutomation;
	}

	public void setWebAutomation(List<WebAutomation> webAutomation) {
		log.info("Set webAutomation list: " + webAutomation);
		this.webAutomation = webAutomation;
	}

	public List<API> getApi() {
		log.info("Returned API list: " + api);
		return api;
	}

	public void setApi(List<API> api) {
		log.info("Set API list: " + api);
		this.api = api;
	}

	public List<Mobile> getMobile() {
		log.info("Returned mobile list: " + mobile);
		return mobile;
	}

	public void setMobile(List<Mobile> mobile) {
		log.info("Set mobile list: " + mobile);
		this.mobile = mobile;
	}

}
