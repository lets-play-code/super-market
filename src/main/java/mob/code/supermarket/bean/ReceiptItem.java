package mob.code.supermarket.bean;

import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;

public class ReceiptItem {
    private final String name;
    private final BuyItem buyItem;
    private Money price;
    private final String unit;
    private final String type;
    private final Quantity quantity;

    public ReceiptItem(BuyItem buyItem, Function<BuyItem, Item> itemFeather) {
        this.buyItem = buyItem;
        this.quantity = buyItem.getQuantity();
        Item item = itemFeather.apply(buyItem);
        this.name = item.getName();
        this.unit = item.getUnit();
        this.price = item.getPrice();
        this.type = item.getType();
        this.checkQuantity();
    }

    public ReceiptItem(String name, Money price, String unit, String type, BuyItem buyItem) {
        this.name = name;
        this.quantity = buyItem.getQuantity();
        this.price = price;
        this.unit = unit;
        this.type = type;
        this.buyItem = buyItem;
    }

    public void checkQuantity() {
        quantity.ensureNotZero(buyItem.getBarcode());
        if (isPackaged()) {
            checkPackaged(buyItem, quantity);
            return;
        }
        checkBulk(buyItem, quantity);
    }

    private void checkBulk(BuyItem buyItem, Quantity quantity) {
        if (!buyItem.hasQuantity()) {
            throw new WrongQuantityException(buyItem.getBarcode());
        }
        quantity.assertBulkQuantityLegal(buyItem.getBarcode());
    }

    private void checkPackaged(BuyItem buyItem, Quantity quantity) {
        quantity.assertIsInteger(buyItem.getBarcode());
    }

    public String format() {
        return name + ": " + getQuantity() + getUnitPart() + " x " + price.format() + " --- " + totalMoney().format();
    }

    private String getUnitPart() {
        return Optional.ofNullable(this.unit)
                .filter(str -> !StringUtils.isEmpty(str))
                .map(u -> "(" + u + ")").orElse("");
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

    public Money totalMoney() {
        return this.price.times(this.quantity);
    }
}
