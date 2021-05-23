package mob.code.supermarket.bean;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ReceiptItemTest {
    @Test
    public void should_format_single_receipt_item_without_unit() {
        ReceiptItem pizza = new ReceiptItem("pizza", 1, "", 15.00);
        assertThat(pizza.format(),is("pizza: 1 x 15.00 --- 15.00"));
    }

    @Test
    public void should_format_multiple_receipt_item_with_unit() {
        ReceiptItem milk = new ReceiptItem("milk", 3, "L", 12.30);
        assertThat(milk.format(),is("milk: 3(L) x 12.30 --- 36.90"));
    }
}