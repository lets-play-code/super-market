package mob.code.supermarket.bean;

import org.springframework.util.StringUtils;

import java.util.Optional;

public class ReceiptItem {
    private final String name;
    private final double quantity;
    private final double price;
    private final String unit;
    private final String type;

    public ReceiptItem(String name, double count, double price, String unit, String type, BuyItem buyItem) {
        this.name = name;
        this.quantity = buyItem.getQuantity();
        this.price = price;
        this.unit = unit;
        this.type = type;
        checkQuantity(type, buyItem);
    }

    private void checkQuantity(String type, BuyItem buyItem) {
        Quantity quantity = new Quantity(this.quantity);
        quantity.ensureNotZero(buyItem.getBarcode());
        if (type.equals("0")) {
            quantity.assertIsInteger(buyItem.getBarcode());
        }
        if (type.equals("1")) {
            buyItem.ensureHasQuantity();
            quantity.assertLegal(buyItem.getBarcode());
        }
    }

    public String format() {
        String unitPart = Optional.ofNullable(this.unit)
                .filter(str -> !StringUtils.isEmpty(str))
                .map(u -> "(" + u + ")").orElse("");
        return name + ": " + getQuantity() + unitPart + " x " + new Money(price).format() + " --- " + new Money(total()).format();
    }

    private String getQuantity() {
        if (this.type.equals("0")) {
            return String.valueOf((int) this.quantity);
        }
        return new Quantity(this.quantity).toString();
    }

    public double total() {
        return new Money(this.quantity * this.price).toActual();
    }
}
