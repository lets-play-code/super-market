package mob.code.supermarket.bean;

import io.cucumber.messages.internal.com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class BuyItemsTest {
    @Test
    public void 当有一个商品返回一个BuyItem() throws Exception {
        BuyItems buyItems = new BuyItems(Arrays.asList("1234555"));
        assertThat(buyItems.toList().collect(Collectors.toList()), is(Arrays.asList(new BuyItem("1234555", 1, Arrays.asList("1234555")))));
    }

    @Test
    public void Given有两个相同的商品_When解析_Then合并() throws Exception {
        BuyItems buyItems = new BuyItems(Arrays.asList("1234555", "1234555"));
        assertThat(buyItems.toList().collect(Collectors.toList()), is(Arrays.asList(new BuyItem("1234555", 2, Arrays.asList("1234555","1234555")))));
    }
}
