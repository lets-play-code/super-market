package mob.code.supermarket.bean;

import org.springframework.util.StringUtils;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Optional;

public class ReceiptItem {
    private final String name;
    private final double count;
    private final double price;
    private final String unit;
    private final String type;

    public ReceiptItem(String name, double count, double price, String unit, String type, BuyItem buyItem) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.unit = unit;
        this.type = type;
        if (type.equals("0")) {
            new Quantity(this.count).assertIsInteger(buyItem.getBarcode());
        }
        if (type.equals("1")) {
            buyItem.ensureHasQuantity();
            new Quantity(this.count).assertLegal(buyItem.getBarcode());
        }
    }

    public String format() {
        String unitPart = Optional.ofNullable(this.unit)
                .filter(str -> !StringUtils.isEmpty(str))
                .map(u -> "(" + u + ")").orElse("");
        return name + ": " + getCount() + unitPart + " x " + new Money(price) + " --- " + new Money(total());
    }

    private String getCount() {
        if (this.type.equals("0")) {
            return String.valueOf((int) this.count);
        }
        return new Quantity(this.count).toString();
    }

    public double total() {
        return this.count * this.price;
    }
}
