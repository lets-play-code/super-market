package mob.code.supermarket.bean;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrderTest {
    @Test
    public void should_return_barcode_from_order_input() {
        assertThat(new Order("12345678").barcode(),is("12345678"));
        assertThat(new Order("12345678-2").barcode(),is("12345678"));
    }

    @Test
    public void should_return_count_from_order_input() {
        assertThat(new Order("12345678").count(),is(1));
        assertThat(new Order("12345678-2").count(),is(2));
    }
}