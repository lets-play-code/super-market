package mob.code.supermarket.service;

import mob.code.supermarket.bean.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * description: ItemServiceTest <br>
 * date: 2021/5/22 下午3:28 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
public class ItemServiceTest {

    ItemService itemService = new ItemService();
    @Test
    void given_barcode_then_order() {
        String inbarcode = "12345678";
        Order order = itemService.makeOrder(inbarcode);
    }
}