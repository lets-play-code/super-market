package mob.code.supermarket.service;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.dao.ItemRepository;
import mob.code.supermarket.model.SupermarketException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
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
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void given_barcode_then_order() {
        String inBarcode = "12345678";
        Item item = new Item("12345", "apple", "cn", 2.2d, "0");
        given(itemRepository.getByBarcode(inBarcode)).willReturn(Optional.of(item));
        List<Order> orders = itemService.makeOrders(Arrays.asList(inBarcode));
        Order expectedOrder = new Order(item.getBarcode(), item.getName(), item.getUnit(), item.getPrice(), "", BigDecimal.valueOf(1));
        assertThat(orders.get(0), is(expectedOrder));
    }

    @Test
    public void when_barcode_with_3_qty_then_order_count_3() {
        String barcode = "22345678-3";
        Item item = new Item("22345678", "milk", "L", 15.0d, "1");
        given(itemRepository.getByBarcode("22345678")).willReturn(Optional.of(item));
        List<Order> orders = itemService.makeOrders(Arrays.asList(barcode));
        assertThat(orders.get(0), is(new Order("22345678", "milk", "L", 15.0d, "", BigDecimal.valueOf(3))));
    }

    @Test
    public void when_barcode_with_1_5_qty_then_order_count_1_5() {
        String barcode = "22345678-1.5";
        Item item = new Item("22345678", "milk", "L", 15.0d, "1");
        given(itemRepository.getByBarcode("22345678")).willReturn(Optional.of(item));
        List<Order> orders = itemService.makeOrders(Arrays.asList(barcode));
        assertThat(orders.get(0).getQuantity(), is(BigDecimal.valueOf(1.5d)));
    }


    @Test
    public void when_2_barcode_then_2_order() {
        given(itemRepository.getByBarcode(Mockito.anyString()))
                .willReturn(Optional.of(new Item("22345678", "milk", "L", 15.0d, "1")));
        List<Order> orders = itemService.makeOrders(Arrays.asList("2-1", "1-2"));
        assertThat(2, is(orders.size()));

    }

    @Test
    public void should_throw_Supermarket_Exception_when_the_barcode_is_not_exist() {
        expectedException.expect(SupermarketException.class);
        String errorBarcode = "barcodeNotExist";
        expectedException.expectMessage(format("item doesn't exist: %s", errorBarcode));
        itemService.makeOrders(Arrays.asList(errorBarcode));
    }

    @Test
    public void when_2_duplicate_barcode_then_return_1_order() {
        given(itemRepository.getByBarcode(Mockito.anyString()))
                .willReturn(Optional.of(new Item("123456", "water", "L", 15.0d, "0")));
        List<Order> orders = itemService.makeOrders(Arrays.asList("123456", "123456"));
        assertThat(orders.size(), is(1));
        assertThat(orders.get(0).getBarcode(), is("123456"));
        assertThat(orders.get(0).getQuantity(), is(BigDecimal.valueOf(2)));
    }
    @Test
    public void when_2_duplicate_barcodeAndCount_then_return_1_order() {
        given(itemRepository.getByBarcode(Mockito.anyString()))
                .willReturn(Optional.of(new Item("123456", "water", "L", 15.0d, "1")));
        List<Order> orders = itemService.makeOrders(Arrays.asList("123456-2", "123456"));
        assertThat(orders.size(), is(1));
        assertThat(orders.get(0).getBarcode(), is("123456"));
        assertThat(orders.get(0).getQuantity(), is(BigDecimal.valueOf(3)));
    }

    @Test
    public void should_throw_Supermarket_Exception_when_the_barcode_quantity_is_precision_more_then_1() {
        expectedException.expect(SupermarketException.class);
        String errorBarcode = "123-1.55";
        expectedException.expectMessage(format("wrong quantity of %s", "123"));
        itemService.makeOrders(Arrays.asList(errorBarcode));
    }

    @Test
    public void should_throw_Supermarket_Exception_when_the_barcode_quantity_is_0() {
        expectedException.expect(SupermarketException.class);
        String errorBarcode = "123-0";
        expectedException.expectMessage(format("wrong quantity of %s", "123"));
        itemService.makeOrders(Arrays.asList(errorBarcode));
    }
    @Test
    public void should_throw_Supermarket_Exception_when_the_barcode_quantity_is_decimal_and_type_is_0() {
        given(itemRepository.getByBarcode(Mockito.anyString()))
                .willReturn(Optional.of(new Item("1234567", "pizza", "", 15.0d, "0")));
        expectedException.expect(SupermarketException.class);
        String errorBarcode = "1234567-1.5";
        expectedException.expectMessage(format("wrong quantity of %s", "1234567"));
        itemService.makeOrders(Arrays.asList(errorBarcode));
    }
    @Test
    public void should_throw_Supermarket_Exception_when_the_barcode_quantity_is_not_exist_and_type_is_1() {
        given(itemRepository.getByBarcode(Mockito.anyString()))
                .willReturn(Optional.of(new Item("01234567", "apple", "", 15.0d, "1")));
        expectedException.expect(SupermarketException.class);
        String errorBarcode = "01234567";
        expectedException.expectMessage(format("wrong quantity of %s", "01234567"));
        itemService.makeOrders(Arrays.asList(errorBarcode));
    }




}