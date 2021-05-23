package mob.code.supermarket.controller;

import java.util.*;
import java.util.stream.Collectors;

public class BarcodeParser {

    private final List<QuantifiedBarcode> barcodeList;

    public BarcodeParser(String[] barcodes) {
        barcodeList = Arrays.stream(barcodes)
                .map(QuantifiedBarcode::new)
                .collect(Collectors.groupingBy(QuantifiedBarcode::getBarCode, Collectors.summarizingInt(QuantifiedBarcode::getQuantity)))
                .entrySet()
                .stream()
                .map(entry -> new QuantifiedBarcode(entry.getKey(), entry.getValue().getSum()))
                .collect(Collectors.toList());
    }

    public List<QuantifiedBarcode> getBarcodes() {
        return barcodeList;
    }
}
