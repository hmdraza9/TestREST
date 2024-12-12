package com.ecom.rest;

import Utils.EncryptionUtil;
import test.epam.rest.Task03RESTAutomation;

public class TestRunner {

	public static void main(String[] args) {

		EcomTests ec = new EcomTests();
		ec.ecomGetToken();
		ec.ecomAddToCart();
		ec.ecomGetAllProducts();
		Task03RESTAutomation.createPet();
		Task03RESTAutomation.typicodeUser();
		Task03RESTAutomation.openWeatherMap();// API Not ready, method yet to complete
		try {
			System.out.println(EncryptionUtil.encrypt("1234567", "123"));
			System.out.println("Exception occueeeeerred!!");
		}
		catch(Exception ex){
			System.out.println("Exception occurred!!");
			System.out.println("Hello!");
		}

	}

}
