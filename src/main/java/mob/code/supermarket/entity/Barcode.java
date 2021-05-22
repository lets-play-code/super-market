package mob.code.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class Barcode {
    public static final String SPLITTER = "-";
    private String code;
    private Integer number;

    public static Barcode readBarcode(String barcode) {
        String[] list = barcode.split(SPLITTER);
        if (list.length == 1) {
            return new Barcode(list[0], 1);
        }
        return new Barcode(list[0], Integer.valueOf(list[1]));
    }

    public static List<Barcode> readBarcodes(List<String> barcodes) {
        List<Barcode> barcodeList = barcodes
                .stream()
                .map(Barcode::readBarcode)
                .collect(Collectors.toList());
        return mergeBarcodes(barcodeList);
    }

    private static List<Barcode> mergeBarcodes(List<Barcode> list) {
        return list.stream()
                .collect(Collectors.groupingByConcurrent(Barcode::getCode))
                .entrySet()
                .stream()
                .map(x -> {
                    int sum = x.getValue().stream().mapToInt(Barcode::getNumber).sum();
                    return new Barcode(x.getKey(), sum);
                }).collect(Collectors.toList());
    }
}
