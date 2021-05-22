package mob.code.supermarket.model;

public class ReceiptItem {
    private final String name;
    private final int quantity;
    private final double price;

    public ReceiptItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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
}
