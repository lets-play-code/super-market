package mob.code.supermarket.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class Item {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private String type;

    public Item(String barcode, String name, String unit, double price, String type) {
        this.barcode = barcode;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.type = type;
    }

}
