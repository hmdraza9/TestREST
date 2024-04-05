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

	private EcomTokenAuthPojo pojoReqEctk;
	private EcomAddToCart ecAd;
	private EcomAddToCartProduct ecAdProd;

	public void ecomGetToken() {

		pojoReqEctk = new EcomTokenAuthPojo();
		pojoReqEctk.setUserEmail("johnedoe070@gmail.com");
		pojoReqEctk.setUserPassword("Test@123");

		RestAssured.baseURI = reqBody.urlEcomAuth;
		vResponse = given().header("Content-Type", "application/json").log().all().body(pojoReqEctk).when().post()
				.then().log().all();

		EcomTokenPojo pojoRespEctk = vResponse.extract().response().as(EcomTokenPojo.class);
		token = pojoRespEctk.getToken();
		userId = pojoRespEctk.getUserId();
		message = pojoRespEctk.getMessage();

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

		RestAssured.baseURI = reqBody.urlEcomAdToCart;
		vResponse = given().header("Content-Type", "application/json").header("Authorization", token).log().all()
				.body(ecAd).when().post().then().log().all();

		vResponse.assertThat().statusCode(StatusCode.OK200);
		System.out.println("Message: "+vResponse.extract().path("message"));

		log.info("Product Added To Cart: " + (new UtilMethods().rawToJson(vResponse.extract().response().asString())
				.getString("message").toString().equalsIgnoreCase("Product Added To Cart")));

	}

	public void ecomGetAllProducts() {

		RestAssured.baseURI = reqBody.urlEcomGetAllProducts;

		vResponse = given().header("Content-Type", "application/json").header("Authorization", token).log().all().when()
				.post().then().log().all();

		EcomGetAllProducts ecGetAllProd = vResponse.extract().response().as(EcomGetAllProducts.class);
		System.out.println("******Prod name: "+vResponse.extract().path("data[1].productName"));

		log.info("ecGetAllProd.getMessage(): " + ecGetAllProd.getMessage());

		log.info("ecGetAllProd.getCount() " + ecGetAllProd.getCount());

		System.out.println("********Getting all products********");

		List<Data> prodList = ecGetAllProd.getData();

		for (int i = 0; i < prodList.size(); i++) {

			log.info("===========================" + (i + 1) + "===========================");
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
			System.out.println("===========================" + (i + 1) + "===========================");
			System.out.println(prodList.get(i).get_id());
			System.out.println(prodList.get(i).getProductName());
			System.out.println(prodList.get(i).getProductCategory());
			System.out.println(prodList.get(i).getProductSubCategory());
			System.out.println(prodList.get(i).getProductPrice());
			System.out.println(prodList.get(i).getProductDescription());
			System.out.println(prodList.get(i).getProductImage());
			System.out.println(prodList.get(i).getProductRating());
			System.out.println(prodList.get(i).getProductTotalOrders());
			System.out.println(prodList.get(i).getProductStatus());
			System.out.println(prodList.get(i).getProductFor());
			System.out.println(prodList.get(i).getProductAddedBy());
			System.out.println(prodList.get(i).get__v());

		}

		vResponse.assertThat().statusCode(StatusCode.OK200);

	}
}
