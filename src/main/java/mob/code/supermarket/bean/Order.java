package mob.code.supermarket.bean;

/**
 * @author Simon
 * @date 2021/5/22 14:38
 */
public class Order {
    private Item item;
    private int quantity;

    public Order(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getBarcode() {
        return null;
    }

    public Item getItem() {
        return this.item;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getAmount() {
        return this.item.getPrice() * this.quantity;
    }
}
