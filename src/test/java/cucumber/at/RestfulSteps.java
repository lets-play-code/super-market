package cucumber.at;

import cucumber.util.RestfulHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import mob.code.supermarket.Application;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertEquals;


@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:acceptance-test/application-test.properties")
@CucumberContextConfiguration
public class RestfulSteps {
    @LocalServerPort
    private int port;

    private ResponseEntity<String> response;

    @When("I {string} the api {string}")
    public void iCallTheApi(String method, String apiName) {
        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
        response = RestfulHelper.connect(port).require(httpMethod, apiName);
    }

    @When("I {string} the api {string} with")
    public void iCallTheApi(String method, String apiName, String body) {
        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
        response = RestfulHelper.connect(port).require(httpMethod, apiName, body);
    }

    @Then("the server response will match {string}")
    public void theServerResponseWillMatchInline(String expectedResponse) throws JSONException {
        verifyResponseMatch(expectedResponse);
    }

    @Then("the server response will match$")
    public void theServerResponseWillMatch(String expectedResponse) throws JSONException {
        verifyResponseMatch(expectedResponse);
    }

    private void verifyResponseMatch(String expectedResponse) throws JSONException {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }
}
