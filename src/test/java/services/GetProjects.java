package services;

import Model.Project;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import steps.Hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetProjects {

    RequestSpecification requestSpecification;

    public GetProjects(){
        requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer "+Hooks.Token );
    }

    public Response GetProjects(){
        return requestSpecification.when().get("" );
    }

    public Project GetProject(String projectId){
        return requestSpecification.when().get("/" + projectId ).as(new TypeRef<Project>() {});
    }

    public Response GetProjectResponse(String projectId){
        return requestSpecification.when().get("/" + projectId);
    }
    public List<Project> GetProjectsList(String ProjectName){
        List<Project> actualProjectList = requestSpecification.when().get("").as(new TypeRef<List<Project>>() {});
        ArrayList<Project> matchingProjects = new ArrayList();
        for (Project project: actualProjectList) {
            if(project.name.equals(ProjectName))
                matchingProjects.add(project);
        }
        return matchingProjects;
    }
}
