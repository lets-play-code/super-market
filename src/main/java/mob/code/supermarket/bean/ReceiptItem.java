package mob.code.supermarket.bean;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReceiptItem {
    private final String name;
    private final int count;
    private final double price;

    public ReceiptItem(String name, int count, double price) {

        this.name = name;
        this.count = count;
        this.price = price;
    }

    public String format() {
        return name + ": " + count + " x " + new BigDecimal(price).setScale(2).toString() + " --- " + new BigDecimal(price).setScale(2).toString();
    }
}
