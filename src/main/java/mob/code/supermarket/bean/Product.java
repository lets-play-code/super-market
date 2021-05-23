package mob.code.supermarket.bean;

import java.util.Objects;

public class Product {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private String type;

    public Product(String barcode, String name, String unit, double price,String type) {
        this.barcode = barcode;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.type = type;
    }
    public String barcode() {
        return this.barcode;
    }

    public String name() {
        return this.name;
    }

    public String unit() {
        return this.unit;
    }

    public double price() {
        return this.price;
    }

    public String type() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(barcode, product.barcode) && Objects.equals(name, product.name) && Objects.equals(unit, product.unit) && Objects.equals(type, product.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, unit, price, type);
    }
}
