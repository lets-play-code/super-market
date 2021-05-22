package cucumber.ut;

import mob.code.supermarket.bean.Receipt;
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
    public void print_when_items_is_empty() throws Exception {
        Receipt receipt = Receipt.of(Collections.emptyList());
        List<String> output = receipt.output();
        assertThat(output, is(Arrays.asList(header, split, "total: 0.00(CNY)", footer)));
    }
}
