package mob.code.supermarket.bean;


import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {
    @Test
    public void should_get_amount_with_floor() {
        Order order = Order.create(
                new Item("123", "mild", "", 12.23, "1"),
                BigDecimal.valueOf(3.3)
        );
        assertThat(order.getAmount()).isEqualTo(40.35);
    }
}