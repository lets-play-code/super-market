package mob.code.supermarket.model;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dto.ScanInput;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemTest {
    @Test
    public void testSubTotal() {
        Item item = new Item("123", "A", "", 2.3, "weight");
        ScanInput input = new ScanInput("123-2");
        OrderItem orderItem = new OrderItem(item, input);
        assertThat(orderItem.getSubTotal()).isEqualTo(4.6);
    }
}