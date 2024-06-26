package test.pojo.courses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CourseMobile {

	private static final Logger log = LogManager.getLogger(CourseMobile.class);

	private String courseTitle;
	private String price;

	public String getCourseTitle() {
		log.info("Returned course Title: " + courseTitle);
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		log.info("Set course Title: " + courseTitle);
		this.courseTitle = courseTitle;
	}

	public String getPrice() {
		log.info("Returned price: " + price);
		return price;
	}

	public void setPrice(String price) {
		log.info("Set price: " + price);
		this.price = price;
	}
}
