package mob.code.supermarket.legacy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import mob.code.supermarket.bean.BarCodeException;
import org.junit.Test;

public class BarcodeReaderTest2 {

    @Test
    public void 当输入条形码为空时返回空() {
        List<String> barcode = new BarcodeReader().getBarcode(null);
        assertThat(barcode, is(Arrays.asList()));
    }

    @Test
    public void 当输入0() {
        String  input = " _ \n"+
            "| |\n"+
            "|_|\n"+
            "\n";
        List<String> barcode = BarcodeReader.barcodeFactory().getBarcode(input);
        assertThat(barcode, is(Arrays.asList("0")));
    }

    @Test
    public void 当输入一个条形码时返回一个barcode() {
        String  input = "    _  _     _  _  _  _ \n"+
            "|   _| _||_||_ |_   ||_|\n"+
            "|  |_  _|  | _||_|  ||_|\n"+
            "";
        List<String> barcode = BarcodeReader.barcodeFactory().getBarcode(input);
        assertThat(barcode, is(Arrays.asList("12345678")));
    }


    @Test
    public void 当输入两个条形码时返回两个barcode() {
        String  input = "    _  _     _  _  _  _ \n"+
            "|   _| _||_||_ |_   ||_|\n"+
            "|  |_  _|  | _||_|  ||_|\n"+
            "\n"+
            " _  _  _     _  _  _  _ \n"+
            " _| _| _||_||_ |_   ||_|\n"+
            "|_ |_  _|  | _||_|  ||_|\n"+
            "3.5";
        List<String> barcode = BarcodeReader.barcodeFactory().getBarcode(input);
        assertThat(barcode, is(Arrays.asList("12345678","22345678-3.5")));
    }



    @Test(expected = BarCodeException.class)
    public void 当输入一个无法识别的条形码则返回异常() {
        String  input = "    _  _    a  _  _  _ \n"+
            "|   _| _||_||_ |    ||_|\n"+
            "|  |_  _|  | _||_|  ||_|\n"+
            "";
        List<String> barcode = BarcodeReader.barcodeFactory().getBarcode(input);
    }
}