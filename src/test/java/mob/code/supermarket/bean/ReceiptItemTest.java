package mob.code.supermarket.bean;

import org.junit.Test;

public class ReceiptItemTest {

    public static final BuyItem BUY_ITEM = new BuyItem("123456", 1, "");

    @Test(expected = WrongQuantityException.class)
    public void 当类型是0数量不是整数抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem("", 2.1, 2.0, "", "0", BUY_ITEM);
    }

    @Test
    public void 当类型是0数量是整数_不抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem("", 2.0, 2.0, "", "0", BUY_ITEM);
    }

    @Test(expected = WrongQuantityException.class)
    public void Given类型是1_When数量是两位小数_Then抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem("", 2.11, 2.0, "", "1", BUY_ITEM);
    }
    @Test(expected = WrongQuantityException.class)
    public void Given类型是1_When原始记录有一条没有重量_Then抛出异常() throws Exception {
        ReceiptItem receiptItem = new ReceiptItem("", 2.11, 2.0, "", "1", new BuyItem("",2.11,"123456"));
    }
}
