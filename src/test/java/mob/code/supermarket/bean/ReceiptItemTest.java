package mob.code.supermarket.bean;

import org.junit.Test;

import java.math.BigDecimal;

public class ReceiptItemTest {


    @Test(expected = WrongQuantityException.class)
    public void 当类型是0数量不是整数抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem(new BuyItem("123456", "2.1", ""), buyItem1 -> new Item(buyItem1.getBarcode(), "", "", new Money(new BigDecimal(2.0)).getValue(), "0"));
        receiptItem.checkQuantity();

    }

    @Test(expected = WrongQuantityException.class)
    public void 当数量是0抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem(new BuyItem("123456", "0", ""), buyItem1 -> new Item(buyItem1.getBarcode(), "", "", new Money(new BigDecimal(2.0)).getValue(), "0"));
        receiptItem.checkQuantity();
    }

    @Test
    public void 当类型是0数量是整数_不抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem(new BuyItem("123456", "2.0", ""), buyItem1 -> new Item(buyItem1.getBarcode(), "", "", new Money(new BigDecimal(2.0)).getValue(), "0"));
        receiptItem.checkQuantity();
    }

    @Test(expected = WrongQuantityException.class)
    public void Given类型是1_When数量是两位小数_Then抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem(new BuyItem("123456", "2.11", ""), buyItem1 -> new Item(buyItem1.getBarcode(), "", "", new Money(new BigDecimal(2.0)).getValue(), "1"));
        receiptItem.checkQuantity();

    }

    @Test(expected = WrongQuantityException.class)
    public void Given类型是1_When原始记录有一条没有重量_Then抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem(new BuyItem("", "2.11", "123456"), buyItem1 -> new Item(buyItem1.getBarcode(), "", "", new Money(new BigDecimal(2.0)).getValue(), "1"));
        receiptItem.checkQuantity();
    }
}
