package mob.code.supermarket.bean;

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
    private String type = "0";

    @Test
    public void print_when_items_is_empty() {
        Receipt receipt = Receipt.of(Collections.emptyList());
        List<String> output = receipt.output();
        assertThat(output.get(2), is("total: 0.00(CNY)"));
    }

    @Test
    public void 当有一个商品() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("pizza", 15.00, "", type, new BuyItem("", 1, "1123-1"))));
        assertThat(receipt.output(), is(Arrays.asList(header, "pizza: 1 x 15.00 --- 15.00", split, "total: 15.00(CNY)", footer)));
    }

    @Test
    public void 当有一个商品带有单位() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 15.00, "L", type, new BuyItem("", 1, "1123-1"))));
        assertThat(receipt.output(), is(Arrays.asList(header, "milk: 1(L) x 15.00 --- 15.00", split, "total: 15.00(CNY)", footer)));
    }

    @Test
    public void 当有1个商品有2份() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 15.00, "L", type, new BuyItem("", 2, "1123-1"))));
        assertThat(receipt.output(), is(Arrays.asList(header, "milk: 2(L) x 15.00 --- 30.00", split, "total: 30.00(CNY)", footer)));
    }

    @Test
    public void 当类型是1的时候数量保留1位() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 15.00, "L", "1", new BuyItem("", 2.2, "1123-1"))));
        assertThat(receipt.output().get(1), is("milk: 2.2(L) x 15.00 --- 33.00"));
    }

    @Test
    public void 当类型是1的时候数量小数超过2保留1位() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 15.00, "L", "1", new BuyItem("", 2.20000001, "1123-1"))));
        assertThat(receipt.output().get(1), is("milk: 2.2(L) x 15.00 --- 33.00"));
    }

    @Test
    public void 当总价有尾部舍弃2位小数后面的() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 15.09, "L", "1", new BuyItem("", 3.5, "1123-1"))));
        assertThat(receipt.output().get(1), is("milk: 3.5(L) x 15.09 --- 52.81"));
    }

    @Test
    public void 当总价有尾部舍弃2位小数后面的1() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(new ReceiptItem("milk", 13.07, "L", "1", new BuyItem("", 0.5, "1123-1"))));
        assertThat(receipt.output().get(1), is("milk: 0.5(L) x 13.07 --- 6.53"));
    }

    @Test
    public void 当两个总价都舍弃尾部按舍弃后都累计() throws Exception {
        Receipt receipt = Receipt.of(Arrays.asList(
                new ReceiptItem("milk", 15.09, "L", "1", new BuyItem("", 3.5, "1123-1")),
                new ReceiptItem("milk", 15.09, "L", "1", new BuyItem("", 3.5, "1123-1"))
        ));
        assertThat(receipt.output().get(4), is("total: 105.62(CNY)"));

    }
}
