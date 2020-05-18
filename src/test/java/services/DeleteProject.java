package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import steps.Hooks;

public class DeleteProject {
    RequestSpecification requestSpecification;

    public DeleteProject(){
        requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer "+ Hooks.Token );
    }

    public Response DeleteProject(String ProjectId){
        return requestSpecification.when().delete("/" + String.valueOf(ProjectId));
    }
}
