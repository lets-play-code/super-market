package mob.code.supermarket.bean;


import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ReceiptTest {
    @Test
    public void should_print_receipt_content() {
        Receipt receipt = new Receipt();
        receipt.add(new ReceiptItem("pizza",1,"",15.00));
        receipt.add(new ReceiptItem("milk",3,"L",12.30));
        assertThat(receipt.print(),is(Arrays.asList(
                "****** SuperMarket receipt ******",
                "pizza: 1 x 15.00 --- 15.00",
                "milk: 3(L) x 12.30 --- 36.90",
                "---------------------------------",
                "total: 51.90(CNY)",
                "*********************************")));
    }
}