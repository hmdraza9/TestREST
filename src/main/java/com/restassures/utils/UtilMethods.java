package com.restassures.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

			i = (int) (Math.random() * 1000 / 1);
			if (i >= min && i <= max) {
				randSet.add(i);
//				System.out.println(i);
			}
//			System.out.println("randSet.size(): " + randSet.size());
		}
		return randSet;

	}

}
