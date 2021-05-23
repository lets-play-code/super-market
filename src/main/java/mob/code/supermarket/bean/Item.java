package mob.code.supermarket.bean;

import java.math.BigDecimal;

public class Item {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private Money priceObj;
    private String type;

    public Item(String barcode, String name, String unit, double price, String type) {
        this.barcode = barcode;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.priceObj = new Money(price);
        this.type = type;
    }

    public Item(String barcode, String name, String unit, BigDecimal price, String type) {
        this.barcode = barcode;
        this.name = name;
        this.unit = unit;
        this.price = price.doubleValue();
        this.priceObj = new Money(price.doubleValue());
        this.type = type;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
