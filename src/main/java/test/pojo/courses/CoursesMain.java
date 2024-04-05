package test.pojo.courses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoursesMain {

	private static final Logger log = LogManager.getLogger(CoursesMain.class);

	private String instructor;
	private String url;
	private String services;
	private String expertise;
	private String linkedIn;
	private AllCourses courses;

	public String getInstructor() {
		log.info("Returned instructor: " + instructor);
		return instructor;
	}

	public void setInstructor(String instructor) {
		log.info("Set instructor: " + instructor);
		this.instructor = instructor;
	}

	public String getUrl() {
		log.info("Returned url: " + url);
		return url;
	}

	public void setUrl(String url) {
		log.info("Set url: " + url);
		this.url = url;
	}

	public String getServices() {
		log.info("Returned services: " + services);
		return services;
	}

	public void setServices(String services) {
		log.info("Set services: " + services);
		this.services = services;
	}

	public String getExpertise() {
		log.info("Returned expertise: " + expertise);
		return expertise;
	}

	public void setExpertise(String expertise) {
		log.info("Set expertise: " + expertise);
		this.expertise = expertise;
	}

	public String getLinkedIn() {
		log.info("Returned linkedIn: " + linkedIn);
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		log.info("Set linkedIn: " + linkedIn);
		this.linkedIn = linkedIn;
	}

	public void setCourses(AllCourses courses) {
		this.courses = courses;
	}

	public AllCourses getCourses() {
		return courses;
	}

}
