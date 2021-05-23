package mob.code.supermarket;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.repository.ItemRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class SupermarketControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        initTestData();
    }

    private void initTestData() {
        itemRepository.save(new Item("12345678", "pizza", "", 15, "0"));
        itemRepository.save(new Item("22345678", "milk", "L", 12.3, "1"));
    }

    @After
    public void tearDown() {
        itemRepository.deleteAll();
    }

    @Test
    public void test_ping() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_scan() throws Exception {
        List<Item> all = itemRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        String requestJson = "[\n" +
                "\"12345678\",\n" +
                "\"22345678-3\"\n" +
                "]";

        String expectedResponseJson = "{\"data\":[\"****** SuperMarket receipt ******\",\"pizza: 1 x 15.00 --- 15.00\",\"milk: 3(L) x 12.30 --- 36.90\",\"---------------------------------\",\"total: 51.90(CNY)\",\"*********************************\"],\"error\":null}";

        String actualResponseJson = mockMvc.perform(post("/scan").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(actualResponseJson).isEqualTo(expectedResponseJson);
    }

    @Test
    public void test_scan_with_error() throws Exception {
        String requestJson = "[\n" +
                "\"88888888\"\n" +
                "]";

        String expectedResponseJson = "{\"data\":null,\"error\":\"item doesn't exist: 88888888\"}";

        String actualResponseJson = mockMvc.perform(post("/scan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(actualResponseJson).isEqualTo(expectedResponseJson);
    }
}