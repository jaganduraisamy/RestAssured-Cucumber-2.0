package services;

import io.restassured.response.Response;
import steps.Hooks;
import java.util.Map;
import java.util.UUID;

public class AddProject {

   private void HeaderSetup(){
       Hooks.request.header("Content-Type","application/json");
       Hooks.request.header("X-Request-Id", UUID.randomUUID().toString().replace("-", ""));
   }

   public Response AddProject(Map<String, String> bodyData){
       HeaderSetup();
       return Hooks.request.body(bodyData).when().post("");
   }
}
