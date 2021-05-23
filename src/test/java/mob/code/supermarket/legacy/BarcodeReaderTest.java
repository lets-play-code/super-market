package mob.code.supermarket.legacy;

import org.junit.Test;

public class BarcodeReaderTest {
    @Test
    public void 读取() {
        BarcodeReader barcodeReader = BarcodeReader.barcodeFactory();
        //language=TEXT
        String input = "    _  _     _  _  _  _ \n" +
                "|   _| _||_||_ |_   ||_|\n" +
                "|  |_  _|  | _||_|  ||_|\n" +
                ",";
        barcodeReader.getBarcode(input);
        System.out.println(barcodeReader.barcodes);
    }
}