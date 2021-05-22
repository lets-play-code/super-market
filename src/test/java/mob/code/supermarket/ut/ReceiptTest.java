package mob.code.supermarket.ut;

import mob.code.supermarket.bean.Receipt;
import mob.code.supermarket.bean.ReceiptItem;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReceiptTest {
    private String header = "****** SuperMarket receipt ******";
    private String footer = "*********************************";
    private String split = "---------------------------------";

    @Test
    public void print_when_items_is_empty() {
        Receipt receipt = Receipt.of(Collections.emptyList());
        List<String> output = receipt.output();
        assertThat(output, is(Arrays.asList(header, split, "total: 0.00(CNY)", footer)));
    }

    @Test
    public void 当有一个商品() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("pizza", 1, 15.00, null)));
        assertThat(receipt.output(), is(Arrays.asList(header, "pizza: 1 x 15.00 --- 15.00", split, "total: 15.00(CNY)", footer)));
    }

    @Test
    public void 当有一个商品带有单位() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 1, 15.00, "L")));
        assertThat(receipt.output(), is(Arrays.asList(header, "milk: 1(L) x 15.00 --- 15.00", split, "total: 15.00(CNY)", footer)));
    }

    @Test
    public void 当有1个商品有2份() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 2, 15.00, "L")));
        assertThat(receipt.output(), is(Arrays.asList(header, "milk: 2(L) x 15.00 --- 30.00", split, "total: 30.00(CNY)", footer)));
    }
}
