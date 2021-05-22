package mob.code.supermarket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class SuperMarketTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_ping() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());
    }


    @Test
    public void test_scan() throws Exception {
        List<String> input = List.of(
                "12345678",
                "22345678-3"
        );

        String requestJson = "[\n" +
                "\"12345678\",\n" +
                "\"22345678-3\"\n" +
                "]";

        String expectedResponseJson = "{\"data\":[\"****** SuperMarket receipt ******\",\"pizza: 1 x 15.00 --- 15.00\",\"milk: 3(L) x 12.30 --- 36.90\",\"---------------------------------\",\"total: 51.90(CNY)\",\"*********************************\"],\"error\":null}";

        String actualResponseJson = mockMvc.perform(post("/scan", requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(actualResponseJson).isEqualTo(expectedResponseJson);
//        JSONAssert.assertEquals(expectedResponseJson, actualResponseJson, false);
    }
}
