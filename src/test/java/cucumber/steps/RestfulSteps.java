package cucumber.steps;

import cucumber.util.RestfulHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.cucumber.spring.CucumberContextConfiguration;
import mob.code.supermarket.Application;
import mob.code.supermarket.ItemFactory;
import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.ItemRepository;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class RestfulSteps {
    @LocalServerPort
    private int port;
    @Autowired
    ItemFactory itemFactory;
    @Autowired
    ItemRepository itemRepository;
    private ResponseEntity<String> response;
    private List<String> scanData;

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

    @Given("条码扫描数据有")
    public void 条码扫描数据有(List<Map<String, String>> items) {
        this.scanData = items.stream().map(item -> item.get("条目")).collect(Collectors.toList());
    }

    @And("有商品")
    public void 有商品(List<Map<String, String>> items) {
        itemFactory.clear();
        items.forEach(itemMap -> {
            Item item = new Item(itemMap.get("条码"),
                    itemMap.get("名称"), itemMap.get("单位"), Double.parseDouble(itemMap.get("价格")), itemMap.get("类型"));
            itemRepository.save(item);
        });
    }

    @Then("扫描条码结果为")
    public void 扫描条码结果为(String content) throws JSONException {
        HttpMethod httpMethod = HttpMethod.valueOf("POST");
        response = RestfulHelper.connect(port).require(httpMethod, "/scan", new Gson().toJson(scanData));
        JSONAssert.assertEquals(content, response.getBody(), false);
    }
}
