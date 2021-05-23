package mob.code.supermarket.entity;

import mob.code.supermarket.model.SupermarketException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReceiptItemTest {
    @Test
    public void 测试小票条目字符串() {
        ReceiptItem pizza = new ReceiptItem("pizza", 1.0f, new BigDecimal("15.00"), "", "individual");
        String actual = pizza.itemInfo();
        String expected = "pizza: 1 x 15.00 --- 15.00";
        assertThat(actual, is(expected));
    }

    @Test
    public void 当商品数量大于一个时正确计算价格() {
        ReceiptItem pizza = new ReceiptItem("pizza", 2.0f, new BigDecimal("15.00"), "", "individual");
        String actual = pizza.itemInfo();
        String expected = "pizza: 2 x 15.00 --- 30.00";
        assertThat(actual, is(expected));
    }

    @Test(expected = SupermarketException.class)
    public void 数量是否为整数() {
        ReceiptItem pizza = new ReceiptItem("pizza", 1.0f, new BigDecimal("15.00"), "", "individual");
        boolean actual = pizza.isCountInteger();
        assertThat(actual, is(true));

        new ReceiptItem("pizza", 1.2f, new BigDecimal("15.00"), "", "individual");
    }
}