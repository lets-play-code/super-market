package mob.code.supermarket.model;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dto.ScanInput;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

public class ReceiptTest {
    @Test
    public void output_should_have_head_foot_separator() {
        List<String> output = new Receipt().output();
        assertThat(output)
                .startsWith(Receipt.HEAD)
                .endsWith(Receipt.FOOT)
                .contains(Receipt.SEPARATOR);
    }

    @Test
    public void should_include_item_in_output() {
        Receipt receipt = new Receipt();
        receipt.addItem(new OrderItem(new Item("001", "it", "", 2, "0"), new ScanInput("001")));
        assertThat(receipt.output()).contains("it: 1 x 2.00 --- 2.00", atIndex(1));
    }

    @Test
    public void should_include_unit_and_count_in_output() {
        Receipt receipt = new Receipt();
        receipt.addItem(new OrderItem(new Item("002", "it", "KG", 4.1, "1"), new ScanInput("002-3")));
        assertThat(receipt.output()).contains("it: 3(KG) x 4.10 --- 12.30", atIndex(1));
    }

    @Test
    public void should_show_total_in_output() {
        Receipt receipt = new Receipt();
        receipt.addItem(new OrderItem(new Item("001", "itA", "", 2, "0"), new ScanInput("001")));
        receipt.addItem(new OrderItem(new Item("002", "itB", "KG", 4.1, "1"), new ScanInput("002-3")));
        assertThat(receipt.output()).contains("total: 14.30(CNY)", atIndex(4));
    }
}