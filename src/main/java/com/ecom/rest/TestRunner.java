package com.ecom.rest;

public class TestRunner {

	public static void main(String[] args) {

		EcomTests ec = new EcomTests();
		ec.ecomGetToken();
		ec.ecomAddToCart();
		
	}

}
