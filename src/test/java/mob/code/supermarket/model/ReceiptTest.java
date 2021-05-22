package mob.code.supermarket.model;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReceiptTest {
    @Test
    public void test_receipt_formatting_with_no_item() {
        Receipt receipt = new Receipt();
        String[] expected = new String[] {
                "****** SuperMarket receipt ******",
                "---------------------------------",
                "total: 0.00(CNY)",
                "*********************************",
        };
        assertThat(receipt.format()).isEqualTo(expected);
    }

    @Test
    public void test_receipt_formatting_with_one_item() {
        Receipt receipt = new Receipt();
        receipt.add(new ReceiptItem("pizza", 1, 15.00 ));
        String[] expected = new String[] {
                "****** SuperMarket receipt ******",
                "pizza: 1 x 15.00 --- 15.00",
                "---------------------------------",
                "total: 15.00(CNY)",
                "*********************************",
        };
        assertThat(receipt.format()).isEqualTo(expected);
    }

    @Test
    public void test_receipt_formatting_with_two_of_the_same_items() {
        Receipt receipt = new Receipt();
        receipt.add(new ReceiptItem("pizza", 2, 15.00 ));
        String[] expected = new String[] {
                "****** SuperMarket receipt ******",
                "pizza: 2 x 15.00 --- 30.00",
                "---------------------------------",
                "total: 30.00(CNY)",
                "*********************************",
        };
        assertThat(receipt.format()).isEqualTo(expected);
    }

    @Test
    public void test_receipt_formatting_with_two_of_the_different_items() {
        Receipt receipt = new Receipt();
        receipt.add(new ReceiptItem("pizza", 2, 15.00 ));
        receipt.add(new ReceiptItem("coffee", 1, 10.00 ));
        String[] expected = new String[] {
                "****** SuperMarket receipt ******",
                "pizza: 2 x 15.00 --- 30.00",
                "coffee: 1 x 10.00 --- 10.00",
                "---------------------------------",
                "total: 40.00(CNY)",
                "*********************************",
        };
        assertThat(receipt.format()).isEqualTo(expected);
    }
}
