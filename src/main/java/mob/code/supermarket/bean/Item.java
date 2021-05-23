package mob.code.supermarket.bean;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Item {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private Money priceObj;
    private String type;

    public Item(String barcode, String name, String unit, BigDecimal price, String type) {
        this.barcode = barcode;
        this.name = name;
        this.unit = unit;
        this.price = price.doubleValue();
        this.priceObj = new Money(price.doubleValue());
        this.type = type;
    }


}
