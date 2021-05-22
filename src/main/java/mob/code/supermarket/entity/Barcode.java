package mob.code.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Barcode {
    private String code;
    private Integer number;

    public static Barcode fromBarcodeString(String barcode) {
        String[] list = barcode.split("-");
        if (list.length == 1) {
            return new Barcode(list[0], 1);
        }
        return new Barcode(list[0], Integer.valueOf(list[1]));
    }
}
