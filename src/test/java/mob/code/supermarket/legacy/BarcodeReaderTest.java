package mob.code.supermarket.legacy;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class BarcodeReaderTest {
    @Test
    public void input_1() throws Exception {
        BarcodeReader barcodeReader = BarcodeReader.barcodeFactory();
        barcodeReader.getBarcode(
                "   \n" +
                        "|  \n" +
                        "|  \n" +
                        "   \n");
        assertThat(barcodeReader.barcodes, is(Arrays.asList("1")));
    }

    @Test
    public void input_2() throws Exception {
        BarcodeReader barcodeReader = BarcodeReader.barcodeFactory();
        barcodeReader.getBarcode(
                " _ \n" +
                        " _|\n" +
                        "|_ \n" +
                        "   \n");
        assertThat(barcodeReader.barcodes, is(Arrays.asList("2")));
    }

    @Test
    public void input_3() throws Exception {
        BarcodeReader barcodeReader = BarcodeReader.barcodeFactory();
        barcodeReader.getBarcode(
                " _ \n" +
                        " _|\n" +
                        " _|\n" +
                        "   \n");
        assertThat(barcodeReader.barcodes, is(Arrays.asList("3")));
    }

    @Test
    public void input_4() throws Exception {
        BarcodeReader barcodeReader = BarcodeReader.barcodeFactory();
        barcodeReader.getBarcode(
                "   \n" +
                        "|_|\n" +
                        "  |\n" +
                        "   \n");
        assertThat(barcodeReader.barcodes, is(Arrays.asList("4")));
    }
    @Test
    public void input_5() throws Exception {
        BarcodeReader barcodeReader = BarcodeReader.barcodeFactory();
        barcodeReader.getBarcode(
                " _ \n" +
                        "|_ \n" +
                        " _|\n" +
                        "   \n");
        assertThat(barcodeReader.barcodes, is(Arrays.asList("5")));
    }
}
