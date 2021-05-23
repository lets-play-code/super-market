package mob.code.supermarket.controller;

import mob.code.supermarket.model.SupermarketException;

import java.util.*;
import java.util.stream.Collectors;

public class BarcodeParser {

    private final List<QuantifiedBarcode> barcodeList;

    public BarcodeParser(String[] barcodes) {
        checkNoMoreThanOneDecimal(barcodes);
        barcodeList = parse(barcodes);
    }

    private List<QuantifiedBarcode> parse(String[] barcodes) {
        return Arrays.stream(barcodes)
                .map(QuantifiedBarcode::new)
                .collect(Collectors.groupingBy(QuantifiedBarcode::getBarCode, Collectors.summarizingDouble(QuantifiedBarcode::getQuantity)))
                .entrySet()
                .stream()
                .map(entry -> new QuantifiedBarcode(entry.getKey(), entry.getValue().getSum()))
                .collect(Collectors.toList());
    }

    private void checkNoMoreThanOneDecimal(String[] barcodes) {
        for (String barcode : barcodes) {
            checkNoMoreThanOneDecimal(barcode);
        }
    }

    private void checkNoMoreThanOneDecimal(String barcode) {
        if (!barcode.contains(".")) {
            return;
        }
        String decimalStr = barcode.substring(barcode.indexOf(".") + 1);
        if (decimalStr.length() > 1) {
            throw new SupermarketException("商品数量不支持1位以上的小数");
        }
    }

    public List<QuantifiedBarcode> getBarcodes() {
        return barcodeList;
    }
}
