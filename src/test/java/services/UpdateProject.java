package services;

import io.restassured.response.Response;
import steps.Hooks;

import java.util.Map;
import java.util.UUID;

public class UpdateProject {

    private void HeaderSetup(){
        Hooks.request.header("Content-Type","application/json");
        Hooks.request.header("X-Request-Id", UUID.randomUUID().toString().replace("-", ""));
    }

    public Response UpdateProject(String projectId, Map<String, String> bodyDataToChange){
        HeaderSetup();
        return Hooks.request.body(bodyDataToChange).when().post("/" + projectId);
    }
}
