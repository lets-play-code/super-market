package mob.code.supermarket.bean;

import org.springframework.util.StringUtils;

import java.util.Optional;

public class ReceiptItem {
    private final String name;
    private Money price;
    private final String unit;
    private final String type;
    private final Quantity quantity;

    public ReceiptItem(String name, Money price, String unit, String type, BuyItem buyItem) {
        this.name = name;
        this.quantity = buyItem.getQuantity();
        this.price = price;
        this.unit = unit;
        this.type = type;
        checkQuantity(type, buyItem);
    }

    private void checkQuantity(String type, BuyItem buyItem) {
        Quantity quantity = this.quantity;
        quantity.ensureNotZero(buyItem.getBarcode());
        if (isPackaged()) {
            quantity.assertIsInteger(buyItem.getBarcode());
            return;
        }
        if (!buyItem.hasQuantity()) {
            throw new WrongQuantityException(buyItem.getBarcode());
        }
        quantity.assertLegal(buyItem.getBarcode());

    }

    public String format() {
        return name + ": " + getQuantity() + getUnitPart() + " x " + price.format() + " --- " + totalMoney().format();
    }

    private String getUnitPart() {
        String unitPart = Optional.ofNullable(this.unit)
                .filter(str -> !StringUtils.isEmpty(str))
                .map(u -> "(" + u + ")").orElse("");
        return unitPart;
    }

    private String getQuantity() {
        if (isPackaged()) {
            return String.valueOf(this.quantity.toInt());
        }
        return this.quantity.toString();
    }

    private boolean isPackaged() {
        return this.type.equals("0");
    }

    public double total() {
        return totalMoney().toActual();
    }

    public Money totalMoney() {
        return this.price.times(this.quantity);
    }
}
