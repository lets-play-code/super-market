package mob.code.supermarket.model;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dto.ScanInput;

public class OrderItem {
    private final Item item;
    private final ScanInput input;

    public OrderItem(Item item, ScanInput input) {

        this.item = item;
        this.input = input;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return input.getCount();
    }

    double getSubTotal() {
        return input.getCount() * item.getPrice();
    }
}
