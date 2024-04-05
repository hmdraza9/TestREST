package com.ecom.rest;

import test.epam.rest.Task03RESTAutomation;

public class TestRunner {

	public static void main(String[] args) {

		EcomTests ec = new EcomTests();
		ec.ecomGetToken();
		ec.ecomAddToCart();
		ec.ecomGetAllProducts();
		Task03RESTAutomation.createPet();
		Task03RESTAutomation.typicodeUser();
//		Task03RESTAutomation.epamEventsInEnglish();// EPAM Events url not working
		Task03RESTAutomation.openWeatherMap();// API Not ready, method yet to complete

	}

}
