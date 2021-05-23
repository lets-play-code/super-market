package mob.code.supermarket.bean;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Item {
    private String barcode;
    private String name;
    private String unit;
    private Money price;
    private String type;

    public Item(String barcode, String name, String unit, BigDecimal price, String type) {
        this.barcode = barcode;
        this.name = name;
        this.unit = unit;
        this.price = new Money(price);
        this.type = type;
    }


}
