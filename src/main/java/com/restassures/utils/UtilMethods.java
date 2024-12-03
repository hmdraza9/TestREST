package com.restassures.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.restassured.path.json.JsonPath;

public class UtilMethods {

	private static final Logger log = LogManager.getLogger(UtilMethods.class);

	public JsonPath rawToJson(String response) {

		log.info("In " + (new Throwable().getStackTrace()[0].getMethodName()));

		JsonPath js1 = new JsonPath(response);

		return js1;

	}

	public Set<Integer> randBetween(int min, int max, int size) {

		int i = 0;

		Set<Integer> randSet = new HashSet<Integer>();

		while (randSet.size() < size) {

			i = (int) (Math.random() * 1000);
			if (i >= min && i <= max) {
				randSet.add(i);
			}
		}
		return randSet;

	}

	public static String getTime() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS");
		return sdf.format(cal.getTime());
	}

	public void ts(WebDriver driver) throws IOException {

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File scrFile = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("Screenshots/hello." + UtilMethods.getTime() + ".png"));
	}
}
