package mob.code.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Barcode {
    public static final String SPLITTER = "-";
    private String code;
    private Integer number;

    public static Barcode fromBarcodeString(String barcode) {
        String[] list = barcode.split(SPLITTER);
        if (list.length == 1) {
            return new Barcode(list[0], 1);
        }
        return new Barcode(list[0], Integer.valueOf(list[1]));
    }
}
