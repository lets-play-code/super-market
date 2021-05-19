package mob.code.supermarket.controller;


import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dao.ItemDao;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SupermarketControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ItemDao dao;

    private MockMvc mockMvc;

    @Before
    public void beforeClass() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_throw_exception_when_barcode_not_exist() throws Exception {
        when(dao.findItem(any())).thenReturn(Optional.empty());
        mockMvc.perform(post("/scan")
                .content("[\"123456789\"]")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("item doesn't exist: 123456789"));
    }

    @Test
    public void should_return_receipt_when_barcodes_valid() throws Exception {
        when(dao.findItem("123456789")).thenReturn(Optional.of(new Item("123456789", "A", "", 2.5, "individual")));
        when(dao.findItem("223456789")).thenReturn(Optional.of(new Item("223456789", "B", "L", 3.5, "weight")));
        mockMvc.perform(post("/scan")
                .content("[\"123456789\", \"223456789-2\"]")

                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data")
                        .value(CoreMatchers.hasItems(
                                "****** SuperMarket receipt ******",
                                "A: 1 x 2.50 --- 2.50",
                                "B: 2(L) x 3.50 --- 7.00",
                                "---------------------------------",
                                "total: 9.50(CNY)",
                                "*********************************")));
    }
}
