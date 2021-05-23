package mob.code.supermarket.bean;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BuyItemTest {


    @Test
    public void 当没有数量缺省为1() throws Exception {
        BuyItem from = BuyItem.from("12345678");
        assertThat(from.getBarcode(), is("12345678"));
        assertThat(from.getQuantity(), is(new Quantity("1")));
    }


    @Test
    public void 当有数量则返回具体数量() throws Exception {
        BuyItem buyItem = BuyItem.from("12345678-2");
        assertThat(buyItem.getBarcode(), is("12345678"));
        assertThat(buyItem.getQuantity().toString(), is(new Quantity("2.0").toString()));
    }


}
