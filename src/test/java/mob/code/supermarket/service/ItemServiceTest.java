package mob.code.supermarket.service;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.dao.ItemDao;
import mob.code.supermarket.model.SupermarketException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;


/**
 * description: ItemServiceTest <br>
 * date: 2021/5/22 下午3:28 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    ItemService itemService;
    @Mock
    ItemDao itemDao;

    @Test
    public void given_barcode_then_order() {
        String inbarcode = "12345678";
        Item item = new Item("12345", "apple", "cn", 2.2d, "1");
        given(itemDao.getItem(Mockito.any())).willReturn(Optional.of(item));
        Order order = itemService.makeOrder(inbarcode);
        Order order1 = new Order(item.getBarcode(), item.getName(), item.getUnit(), item.getPrice(), "", 1);
        assertThat(order, is(order1));
    }

    @Test
    public void when_barcode_with_3_qty_then_order_count_3() {
        String barcode = "22345678-3";
        Item item = new Item("22345678", "milk", "L", 15.0d, "1");
        given(itemDao.getItem(Mockito.any())).willReturn(Optional.of(item));
        Order order = itemService.makeOrder(barcode);
        assertThat(order, is(new Order("22345678", "milk", "L", 15.0d, "", 3)));
    }

    @Test
    public void when_2_barcode_then_2_order() {
        given(itemDao.getItem(Mockito.anyString())).willReturn(Optional.of(new Item("22345678", "milk", "L", 15.0d, "1")));
        List<Order> orders = itemService.makeOrders(Arrays.asList("", ""));
        assertThat(2, is(orders.size()));

    }

    @Test
    public void should_throw_Supermarket_Exception_when_the_barcode_is_not_exist() {
        given(itemDao.getItem(Mockito.anyString())).willReturn(Optional.empty());
        expectedException.expect(SupermarketException.class);
        String errorBarcode = "barcodeNotExist";
        expectedException.expectMessage(format("item doesn't exist: %s", errorBarcode));

        itemService.makeOrder(errorBarcode);
    }
}