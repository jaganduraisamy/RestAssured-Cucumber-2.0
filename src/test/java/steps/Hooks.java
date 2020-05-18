package steps;

import com.sun.tools.jxc.ap.Const;
import cucumber.api.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Hooks {

    public static RequestSpecification request;
    public static String Token = "c1688fb57e5c49a62095180fe79b176aa90a70b8";
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://api.todoist.com";
        RestAssured.basePath = "/rest/v1/projects";
    }
}
