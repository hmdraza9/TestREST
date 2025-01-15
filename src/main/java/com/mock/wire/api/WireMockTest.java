package com.mock.wire.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class WireMockTest {

    private static WireMockServer wireMockServer;

    public static void main(String[] args) {

        Map<String, String> req = new HashMap<>();
        req.put("message", "Hello, WireMock!");

        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        try{


            wireMockServer.stubFor(get(urlEqualTo("/api/resource"))
                    .willReturn(aResponse()
                            .withStatus(500)
                            .withHeader("Content-Type", "application/json")
                            .withBody(req.toString())));

            System.out.println("Mock server is running...");


            Response res = given()
                    .baseUri("http://localhost:8080")
                    .when()
                    .get("/api/resource");

            System.out.println("res.asString(): "+res.asString());

            String vResp = res.then().extract().response().asString();
//            res.then().statusCode(500)
//                    .body("message", equalTo("Hello, WireMock!"));

            System.out.println("vResp: "+vResp);

            JsonPath jp = new JsonPath(vResp);

            System.out.println(jp.getString("message"));
        }
        catch(Exception ex){
            ex.getLocalizedMessage();
            ex.printStackTrace();
        }
        finally {
            wireMockServer.stop();
            System.out.println("Mock server stopped!");
        }



    }
}