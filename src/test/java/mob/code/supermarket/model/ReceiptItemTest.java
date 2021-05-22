package mob.code.supermarket.model;

import mob.code.supermarket.bean.Item;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;

public class ReceiptItemTest {

    @Test
    public void 格式化有单位的Item() {
        ReceiptItem receiptItem = new ReceiptItem("milk", 3, 12.30, "L");
        assertThat(receiptItem.format()).isEqualTo("milk: 3(L) x 12.30 --- 36.90");
    }

    @Test
    public void 根据商品和数量创建item() {


        Item pizza=new Item("12345678","pizza",null,15.00,"0");
        //Item milk=new Item("22345678","milk","L",12.30,"1");

        ReceiptItem receiptItem=new ReceiptItem(pizza,2);
        assertThat(receiptItem.format()).isEqualTo("pizza: 2 x 15.00 --- 30.00");

    }
}