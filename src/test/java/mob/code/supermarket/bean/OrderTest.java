package mob.code.supermarket.bean;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {
    @Test
    public void should_create_order() {
        int quantity = 1;
        Order order = new Order("123", "pizza", "个", 100.0, "0", quantity);
        assertThat(order.getBarcode()).isEqualTo("123");
    }
}