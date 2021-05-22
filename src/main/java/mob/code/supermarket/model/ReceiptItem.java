package mob.code.supermarket.model;

import mob.code.supermarket.bean.Item;
import org.springframework.util.StringUtils;

public class ReceiptItem {
    private String name;
    private int quantity;
    private double price;
    private Item item;
    private String unit;

    public ReceiptItem(String name, int quantity, double price, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.unit = unit;
    }

    public ReceiptItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public ReceiptItem(Item item, int quantity) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.unit = item.getUnit();
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return quantity * price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    String format() {
        return String.format("%s: %d%s x %.2f --- %.2f",
                getName(),
                getQuantity(),
                getUnit(),
                getPrice(),
                getCost());
    }

    private String getUnit() {
        if (StringUtils.isEmpty(StringUtils.trimAllWhitespace(unit))) {
            return "";
        }
        return String.format("(%s)", this.unit);
    }
}
