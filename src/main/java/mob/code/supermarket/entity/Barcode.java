package mob.code.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import mob.code.supermarket.model.SupermarketException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class Barcode {
    public static final String SPLITTER = "-";
    private String code;
    private Float number;
    private boolean isNumberUndefined;

    public static Barcode readBarcode(String barcode) {
        String[] list = barcode.split(SPLITTER);
        String code = list[0];
        if (list.length == 1) {
            return new Barcode(code, 1.0f, true);
        }
        String count = list[1];
        if (isWrongCount(count)) {
            throw new SupermarketException("wrong quantity of " + code);
        }
        return new Barcode(code, Float.valueOf(count), false);
    }

    /**
     * 是否超过两位小数
     *
     * @param count
     * @return
     */
    private static boolean isWrongCount(String count) {
        if (Float.parseFloat(count) == 0.0f) {
            return true;
        }
        String[] parts = count.split("\\.");
        return parts.length > 1 && parts[1].length() > 1;
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
                    Double sum = x.getValue().stream().mapToDouble(Barcode::getNumber).sum();
                    boolean isNumberUndefined = x.getValue().stream().anyMatch((e) -> e.isNumberUndefined());
                    return new Barcode(x.getKey(), sum.floatValue(), isNumberUndefined);
                }).collect(Collectors.toList());
    }

    public boolean isNumberInteger() {
        return (number - number.intValue()) == 0;
    }
}
