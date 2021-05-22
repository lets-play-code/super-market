package mob.code.supermarket.entity;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BarcodeTest {

    @Test
    public void 解析BarCode转换() {
        Barcode actual = Barcode.fromBarcodeString("123456");
        assertThat(actual.getCode(), is("123456"));
        assertThat(actual.getNumber(), is(1));
    }

    @Test
    public void 解析BarCode转换多条目() {
        Barcode actual = Barcode.fromBarcodeString("123456-3");
        assertThat(actual.getCode(), is("123456"));
        assertThat(actual.getNumber(), is(3));
    }
}