package mob.code.supermarket.entity;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReceiptItemTest {
    @Test
    public void 测试小票条目字符串() {
        ReceiptItem pizza = new ReceiptItem("pizza", 1, new BigDecimal("15.00"));
        String actual = pizza.itemInfo();
        String expected = "pizza: 1 x 15.00 --- 15.00";
        assertThat(actual, is(expected));
    }

    @Test
    public void 当商品数量大于一个时正确计算价格() {
        ReceiptItem pizza = new ReceiptItem("pizza", 2, new BigDecimal("15.00"));
        String actual = pizza.itemInfo();
        String expected = "pizza: 2 x 15.00 --- 30.00";
        assertThat(actual, is(expected));
    }
}