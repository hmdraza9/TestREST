package test.oauth.REST;

import com.restAPI.practiceClasses.PasswordEncryption;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class PayPalOAuth {


    private static String[] creds = new String[3];
    private static final String configPropertyFile = "src/test/resources/config.properties";
    public static String CLIENT_ID;
    public static String CLIENT_SECRET;
    private static String credUrl;
    private static String access_token;


    public static void main(String[] args) throws IOException {

        credUrl = getProp("credUrl");
        if(getCredentialsFromPasteBin(credUrl)==200)
        {
            setBaseURIForPayPal();
            getTokenFromPayPal();
            getInvoiceTemplateFromPayPal();
        }
    }

    private static String getProp(String prop) throws IOException {
        FileReader fr = new FileReader(configPropertyFile);
        Properties pr = new Properties();
        pr.load(fr);
        String temp = pr.getProperty(prop);
        System.out.println("Property value for prop: "+prop+" returned as: "+temp);
        return temp;
    }

    private static int getCredentialsFromPasteBin(String url) {
        System.out.println("Fetching credentials from PastBin...");
        int statusCode;
        // Make the HTTP GET request using RestAssured
        // The URL of the page to fetch

        // Send a GET request using RestAssured
        Response response = RestAssured.given().get(url);
        statusCode = response.statusCode();
        if(statusCode==200){
            // Get the response body as a string
            String htmlContent = response.getBody().asString();

            // Parse the HTML using Jsoup
            Document document = Jsoup.parse(htmlContent);

            // Find the element with class 'de1'
            Element de1Element = document.select(".de1").first();

            // Extract and print the text from the element
            if (de1Element != null) {
                String dataText = de1Element.text();
                creds = dataText.split(" ");
                CLIENT_ID = PasswordEncryption.decrypt(creds[0], creds[1]);
                CLIENT_SECRET = PasswordEncryption.decrypt(creds[2], creds[1]);
                System.out.println("Credentials fetched from PasteBin...");
            } else {
                System.out.println("Element with class 'de1' not found.");
            }
        }
        else
            System.out.println("ERROR in request to paste bin, status code is: "+statusCode);
        return statusCode;
    }


    private static void setBaseURIForPayPal() throws IOException {
        String temp = getProp("paypalBaseUrl");
        System.out.println("pay pal base URI set to: "+temp);
        RestAssured.baseURI = temp;
    }

    private static void getTokenFromPayPal() {

        //------ get access token

        Response tokenResponse = given().header("Content-Type", "application/x-www-form-urlencoded").auth().preemptive().basic(CLIENT_ID, CLIENT_SECRET).param("grant_type", "client_credentials").post("/v1/oauth2/token");
        access_token = new JsonPath(tokenResponse.asString()).getString("access_token");
        System.out.println("Token request success?: "+(tokenResponse.statusCode()==200));
        System.out.println("Token fetched: "+(access_token.length()>10));
    }

    private static void getInvoiceTemplateFromPayPal() {

        System.out.println("Requesting Invoicing templates.");
        //------ get invoice templates

        Response invTemplateResponse = given().header("Content-Type", "application/json").header("Authorization", "Bearer " + access_token).get("/v2/invoicing/templates");

        System.out.println("Invoicing Templates response success? :"+(invTemplateResponse.statusCode()==200));

        System.out.println("First address: " + (new JsonPath(invTemplateResponse.asString()).getString("addresses[0]")));

        System.out.println("Second address: " + (new JsonPath(invTemplateResponse.asString()).getString("addresses[1]")));

    }


}
