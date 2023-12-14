package test.pojo.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class API {

	private static final Logger log = LogManager.getLogger(API.class);

	private String courseTitle;
	private String price;

	public String getCourseTitle() {
		log.info("Returned courseTitle: "+courseTitle);
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		log.info("Set courseTitle: "+courseTitle);
		this.courseTitle = courseTitle;
	}

	public String getPrice() {
		log.info("Returned price: "+price);
		return price;
	}

	public void setPrice(String price) {
		log.info("Set price: "+price);
		this.price = price;
	}


}
