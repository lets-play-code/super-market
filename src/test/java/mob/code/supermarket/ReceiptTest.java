package mob.code.supermarket;

import mob.code.supermarket.bean.Order;
import mob.code.supermarket.bean.Receipt;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Simon
 * @date 2021/5/22 14:19
 */
public class ReceiptTest {
    @Test
    public void should_print_receipt_items() {
        //given
        //        result.add("pizza: 1 x 15.00 --- 15.00");
        //        result.add("milk: 3(L) x 12.30 --- 36.90");
        Receipt receipt = new Receipt(Arrays.asList(
                new Order("123", "pizza", "", 15, "0", 1),
                new Order("1234", "milk", "L", 12.30, "1", 3)
        ));

        //when
        List<String> printContent = receipt.print();

        //then

        List<String> expectedContent = Arrays.asList(
                "****** SuperMarket receipt ******",
                "pizza: 1 x 15.00 --- 15.00",
                "milk: 3(L) x 12.30 --- 36.90",
                "---------------------------------",
                "total: 51.90(CNY)",
                "*********************************"
        );
        assertThat(printContent).isEqualTo(expectedContent);


    }
}
