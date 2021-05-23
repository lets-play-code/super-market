package mob.code.supermarket.domain;

import mob.code.supermarket.entity.Receipt;
import mob.code.supermarket.entity.ReceiptItem;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptDomainTest {
    @Test
    public void 当输入为披萨时返回账单小票() {
        Receipt receipt = new Receipt();
        receipt.add(new ReceiptItem("pizza", 1.0f, new BigDecimal("15.00"), "", "individual"));
        List<String> strings = receipt.toResult();
        List<String> expected = List.of(
                "****** SuperMarket receipt ******",
                "pizza: 1 x 15.00 --- 15.00",
                "---------------------------------",
                "total: 15.00(CNY)",
                "*********************************");
        Assertions.assertThat(strings).isEqualTo(expected);
    }

    @Test
    public void 当输入两个披萨时返回账单小票() {
        Receipt receipt = new Receipt();
        receipt.add(new ReceiptItem("pizza", 2.0f, new BigDecimal("15.00"), "", "individual"));
        List<String> strings = receipt.toResult();
        List<String> expected = List.of(
                "****** SuperMarket receipt ******",
                "pizza: 2 x 15.00 --- 30.00",
                "---------------------------------",
                "total: 30.00(CNY)",
                "*********************************");
        Assertions.assertThat(strings).isEqualTo(expected);
    }
}