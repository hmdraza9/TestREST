package com.ecom.rest;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restassured.payloads.StatusCode;
import com.restassured.payloads.testDataPayloads;
import com.restassures.utils.UtilMethods;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import test.pojo.ecom.Data;
import test.pojo.ecom.EcomAddToCart;
import test.pojo.ecom.EcomAddToCartProduct;
import test.pojo.ecom.EcomGetAllProducts;
import test.pojo.ecom.EcomTokenAuthPojo;
import test.pojo.ecom.EcomTokenPojo;

public class EcomTests {

	private static final Logger log = LogManager.getLogger(EcomTests.class);
	testDataPayloads reqBody = new testDataPayloads();
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

		RestAssured.baseURI = reqBody.uriEcomAuth;
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

		RestAssured.baseURI = reqBody.uriEcomAdToCart;
		vResponse = given().header("Content-Type", "application/json").header("Authorization", token).log().all()
				.body(ecAd).when().post().then().log().all();

		vResponse.assertThat().statusCode(StatusCode.OK200);

		log.info("Product Added To Cart: " + (new UtilMethods().rawToJson(vResponse.extract().response().asString())
				.getString("message").toString().equalsIgnoreCase("Product Added To Cart")));

	}
	
	public void ecomGetAllProducts() {
						
		RestAssured.baseURI = reqBody.uriEcomGetAllProducts;
		
		vResponse = given().header("Content-Type", "application/json").header("Authorization", token).log().all()
				.when().post().then().log().all();

		EcomGetAllProducts ecGetAllProd = vResponse.extract().response().as(EcomGetAllProducts.class);

		log.info("ecGetAllProd.getMessage(): "+ecGetAllProd.getMessage());

		log.info("ecGetAllProd.getCount() "+ecGetAllProd.getCount());

		List<Data> prodList = ecGetAllProd.getData();
		
		for(int i=0;i<prodList.size();i++) {

			log.info("===========================" + (i+1) + "===========================");
			log.info(prodList.get(i).get_id());
			log.info(prodList.get(i).getProductName());
			log.info(prodList.get(i).getProductCategory());
			log.info(prodList.get(i).getProductSubCategory());
			log.info(prodList.get(i).getProductPrice());
			log.info(prodList.get(i).getProductDescription());
			log.info(prodList.get(i).getProductImage());
			log.info(prodList.get(i).getProductRating());
			log.info(prodList.get(i).getProductTotalOrders());
			log.info(prodList.get(i).getProductStatus());
			log.info(prodList.get(i).getProductFor());
			log.info(prodList.get(i).getProductAddedBy());
			log.info(prodList.get(i).get__v());
			
		}
		
		vResponse.assertThat().statusCode(StatusCode.OK200);
		
	}
}
