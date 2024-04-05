package test.pojo.courses;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllCourses {

	private static final Logger log = LogManager.getLogger(AllCourses.class);

	private List<CourseWebAutomation> webAutomation;
	private List<CourseAPI> api;
	private List<CourseMobile> mobile;

	public List<CourseWebAutomation> getWebAutomation() {
		log.info("Returned webAutomation list: " + webAutomation);
		return webAutomation;
	}

	public void setWebAutomation(List<CourseWebAutomation> webAutomation) {
		log.info("Set webAutomation list: " + webAutomation);
		this.webAutomation = webAutomation;
	}

	public List<CourseAPI> getApi() {
		log.info("Returned API list: " + api);
		return api;
	}

	public void setApi(List<CourseAPI> api) {
		log.info("Set API list: " + api);
		this.api = api;
	}

	public List<CourseMobile> getMobile() {
		log.info("Returned mobile list: " + mobile);
		return mobile;
	}

	public void setMobile(List<CourseMobile> mobile) {
		log.info("Set mobile list: " + mobile);
		this.mobile = mobile;
	}

}
