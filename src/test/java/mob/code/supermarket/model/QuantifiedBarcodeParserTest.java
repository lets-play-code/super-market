package mob.code.supermarket.model;

import mob.code.supermarket.controller.QuantifiedBarcode;
import mob.code.supermarket.controller.BarcodeParser;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class QuantifiedBarcodeParserTest {

    @Test
    public void 汇总重复扫码商品() {
        String[] codes = {"12345678", "12345678", "12345678"};
        BarcodeParser barcodeParser = new BarcodeParser(codes);
        List<QuantifiedBarcode> barcodes = barcodeParser.getBarcodes();
        assertThat(barcodes.size()).isEqualTo(1);
        assertThat(barcodes.get(0).getBarCode()).isEqualTo("12345678");
        assertThat(barcodes.get(0).getQuantity()).isEqualTo(3);
    }

    @Test
    public void 支持小数位称重() {
        String[] codes = {"22345678-1.5"};
        BarcodeParser barcodeParser=new BarcodeParser(codes);
        List<QuantifiedBarcode> barcodes = barcodeParser.getBarcodes();
        assertThat(barcodes.size()).isEqualTo(1);
        assertThat(barcodes.get(0).getBarCode()).isEqualTo("22345678");
        assertThat(barcodes.get(0).getQuantity()).isEqualTo(1.5);

    }
}
