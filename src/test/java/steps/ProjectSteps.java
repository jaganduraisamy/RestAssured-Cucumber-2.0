package steps;

import Model.Project;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import services.AddProject;
import services.DeleteProject;
import services.GetProjects;
import services.UpdateProject;


public class ProjectSteps {
    HashMap<String, String> headers = new HashMap();
    private Project project;
    private GetProjects getProjectsService;
    private AddProject addProjectService;
    private UpdateProject updateProjectService;
    private DeleteProject deleteProjectService;
    public static Response response;

    public ProjectSteps() {
        getProjectsService = new GetProjects();
        addProjectService = new AddProject();
        updateProjectService = new UpdateProject();
        deleteProjectService = new DeleteProject();
    }

    @Given("^I have an authenticated token$")
    public void i_have_an_authenticated_token() throws Throwable {
        Hooks.request = RestAssured.given();
        Hooks.request.header("Authorization", "Bearer "+Hooks.Token );
    }

    @When("^I send Get request to Get all projects$")
    public void I_send_Get_request_to_Get_all_projects() throws Throwable {
        response = getProjectsService.GetProjects();
        System.out.println("Response is " + response.prettyPrint());
    }

    @Then("^I should have the status code \"([^\"]*)\"$")
    public void i_should_have_the_status_code(String statusCode) throws Throwable {
        Assert.assertEquals((long)response.statusCode(), (long)Integer.parseInt(statusCode));
    }

    @When("^I send post request to create new project with name as (.*)$")
    public void iSendPostRequestToCreateNewProjectWithNameAs(String projectName) throws Throwable {
        Map<String, String> map = new HashMap();
        map.put("name", projectName);
        response = addProjectService.AddProject(map);
        System.out.println("Response is " + response.prettyPrint());
        this.project = (Project)response.getBody().as(Project.class);
    }

    @And("^I should see project name is (.*)$")
    public void iShouldSeeProjectNameIsJDTestProject(String projectName) {
        Assert.assertTrue(this.project.name.equals(projectName));
    }

    @And("^I have an existing project with name (.*)$")
    public void iHaveAnExistingProjectWithName(String projectName) {
        List<Project> projectList = getProjectsService.GetProjectsList(projectName);
        this.project = (Project)projectList.get(0);
    }

    @When("^I send update request to update project name as (.*)$")
    public void iSendUpdateRequestToUpdateProjectNameAs(String projectNameToChange) {
        Map<String, String> bodyDataToChange = new HashMap();
        bodyDataToChange.put("name", projectNameToChange);
        response = updateProjectService.UpdateProject(String.valueOf(this.project.id),bodyDataToChange);
        System.out.println("Response is " + response.prettyPrint());
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Then("^I should see project name successfully updated as (.*)$")
    public void iShouldSeeProjectNameSuccessfullyUpdatedAsJDTestProject(String expProjectName) {
        Project newProject = getProjectsService.GetProject(String.valueOf(project.id));
        Assert.assertTrue(newProject.name.equals(expProjectName));
    }

    @When("^I send delete request to delete existing project with name (.*)$")
    public void iSendDeleteRequestToDeleteExistingProjectWithNameContains(String projectNameKeyword) {
        project = getProjectsService.GetProjectsList(projectNameKeyword).get(0);
        response = deleteProjectService.DeleteProject(String.valueOf(project.id));
        System.out.println("Response is " + response.prettyPrint());
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Then("^I should not see project in projects list$")
    public void iShouldNotSeeProjectInProjectsList() {
        response = getProjectsService.GetProjectResponse(String.valueOf(project.id));
        System.out.println("Response is " + response.prettyPrint());
        Assert.assertEquals(response.statusCode(), 404);
        response.then().assertThat().toString().equals("Not Found");
    }

}

