package com.mock.wire.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class WireMockTest {

    private static WireMockServer wireMockServer;
    private static final String credUrl = "https://pastebin.com/hbkZ1467";

    public static void main(String[] args) {
//        runWireMockTest();
        getCredentials(credUrl);
    }

    private static void getCredentials(String url){
        // Make the HTTP GET request using RestAssured
        // The URL of the page to fetch

        // Send a GET request using RestAssured
        Response response = RestAssured.given().get(url);

        // Get the response body as a string
        String htmlContent = response.getBody().asString();

        // Parse the HTML using Jsoup
        Document document = Jsoup.parse(htmlContent);

        // Find the element with class 'de1'
        Element de1Element = document.select(".de1").first();

        // Extract and print the text from the element
        if (de1Element != null) {
            String dataText = de1Element.text();
            System.out.println("Text inside 'de1' element: >\n" + dataText+"\n<");
            System.out.println(">"+dataText.split(" ")[0]+"<");
            System.out.println(">"+dataText.split(" ")[1]+"<");
            System.out.println(">"+dataText.split(" ")[2]+"<");
        } else {
            System.out.println("Element with class 'de1' not found.");
        }
    }

    private static void runWireMockTest() {
        Map<String, String> req = new HashMap<>();
        req.put("message", "Hello, WireMock!");

        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        try {
            // Stub configuration
            wireMockServer.stubFor(get(urlEqualTo("/api/resource"))
                    .willReturn(aResponse()
                            .withStatus(500)
                            .withHeader("Content-Type", "application/json")
                            .withBody(req.toString())));

            System.out.println("Mock server is running...");

            // Sending the request and receiving the response
            Response res = given()
                    .baseUri("http://localhost:8080")
                    .when()
                    .get("/api/resource");

            System.out.println("res.asString(): " + res.asString());

            String vResp = res.then().extract().response().asString();
            System.out.println("vResp: " + vResp);

            JsonPath jp = new JsonPath(vResp);
            System.out.println("Message from response: " + jp.getString("message"));
        } catch (Exception ex) {
            System.err.println("Exception occurred: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        } finally {
            wireMockServer.stop();
            System.out.println("Mock server stopped!");
        }
    }
}
