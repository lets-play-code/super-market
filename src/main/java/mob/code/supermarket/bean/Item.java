package mob.code.supermarket.bean;


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
