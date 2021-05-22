package mob.code.supermarket.entity;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BarcodeTest {

    @Test
    public void 解析BarCode转换() {
        Barcode actual = Barcode.readBarcode("12345678");
        assertThat(actual.getCode(), is("12345678"));
        assertThat(actual.getNumber(), is(1));
    }

    @Test
    public void 解析BarCode转换多条目() {
        Barcode actual = Barcode.readBarcode("12345678-3");
        assertThat(actual.getCode(), is("12345678"));
        assertThat(actual.getNumber(), is(3));
    }

    @Test
    public void 解析BarCodes转换() {
        List<Barcode> actual = Barcode.readBarcodes(List.of("12345678", "12345678", "12345678"));
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0).getCode(), is("12345678"));
        assertThat(actual.get(0).getNumber(), is(3));
    }

    @Test
    public void 解析BarCodes转换多条目() {
        List<Barcode> actual = Barcode.readBarcodes(List.of("12345678", "12345678-3", "12345678-2"));
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0).getCode(), is("12345678"));
        assertThat(actual.get(0).getNumber(), is(6));
    }

    @Test
    public void 解析BarCodes转换多品类多条目() {
        List<Barcode> actual = Barcode.readBarcodes(List.of("12345678", "12345678-3", "12345678-2",
                "123456789", "123456789-3", "123456789-2"));
        assertThat(actual.size(), is(2));
        assertThat(actual.get(1).getCode(), is("12345678"));
        assertThat(actual.get(1).getNumber(), is(6));
        assertThat(actual.get(0).getCode(), is("123456789"));
        assertThat(actual.get(0).getNumber(), is(6));
    }
}