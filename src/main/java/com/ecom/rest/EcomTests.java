package com.ecom.rest;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import test.pojo.ecom.EcomAddToCart;
import test.pojo.ecom.EcomAddToCartProduct;
import test.pojo.ecom.EcomTokenAuthPojo;
import test.pojo.ecom.EcomTokenPojo;

public class EcomTests {

	private static final Logger log = LogManager.getLogger(EcomTests.class);
	private String token;
	private String userId;
	private String message;
	private String prodId = "6262e95ae26b7e1a10e89bf0";
	private ValidatableResponse vResponse;

	private EcomTokenAuthPojo ectk;
	private EcomAddToCart ecAd;
	private EcomAddToCartProduct ecAdProd;

	public void ecomGetToken() {

		ectk = new EcomTokenAuthPojo();
		ectk.setUserEmail("johnedoe070@gmail.com");
		ectk.setUserPassword("Test@123");

		RestAssured.baseURI = "https://rahulshettyacademy.com/api/ecom/auth/login";
		vResponse = given().header("Content-Type", "application/json").log().all().body(ectk).when().post().then().log()
				.all();

		EcomTokenPojo ec = vResponse.extract().response().as(EcomTokenPojo.class);
		token = ec.getToken();
		userId = ec.getUserId();
		message = ec.getMessage();

		log.info("token: " + token);
		log.info("userId: " + userId);
		log.info("message: " + message);
	}

	public void ecomAddToCart() {
		ecAd = new EcomAddToCart();
		ecAdProd = new EcomAddToCartProduct();

		ecAdProd.set_id(prodId);
		ecAdProd.setProductName("zara coat 3");
		ecAd.set_id(userId);
		ecAd.setProduct(ecAdProd);

		RestAssured.baseURI = "https://rahulshettyacademy.com/api/ecom/user/add-to-cart";
		vResponse = given().header("Content-Type", "application/json").header("Authorization", token).log().all()
				.body(ecAd).when().post().then().log().all();

		log.info("Response validation: " + vResponse.assertThat().statusCode(200));

		log.info("Product Added To Cart: "+(new UtilMethods().rawToJson(vResponse.extract().response().toString()).get("message").toString()
				.equalsIgnoreCase("Product Added To Cart")));

	}
}
