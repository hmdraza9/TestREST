package com.ecom.rest;

import Utils.EncryptionUtil;
import test.epam.rest.Task03RESTAutomation;

public class TestRunner {

	public static void main(String[] args) {

		EcomTests ec = new EcomTests();
		System.out.println("1. ec.ecomGetToken();: ");
		ec.ecomGetToken();
		System.out.println("2. ec.ecomAddToCart()");
		ec.ecomAddToCart();
		System.out.println("3. ec.ecomGetAllProducts()");
		ec.ecomGetAllProducts();
		System.out.println("4. Task03RESTAutomation.createPet();");
		Task03RESTAutomation.createPet();
//		Task03RESTAutomation.validateSchemaFroPetApi();
//		System.out.println("5. Task03RESTAutomation.typicodeUser();");
//		Task03RESTAutomation.typicodeUser();
//		System.out.println("6. Task03RESTAutomation.openWeatherMap();");
//		Task03RESTAutomation.openWeatherMap();// API Not ready, method yet to complete

	}

}
