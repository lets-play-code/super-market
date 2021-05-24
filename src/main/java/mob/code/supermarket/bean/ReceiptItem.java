package mob.code.supermarket.bean;

import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;

public class ReceiptItem {
    private final BuyItem buyItem;
    private final Item item;

    public ReceiptItem(BuyItem buyItem, Function<BuyItem, Item> itemFeather) {
        this.buyItem = buyItem;
        this.item = itemFeather.apply(buyItem);
        this.checkQuantity();
    }

    public void checkQuantity() {
        if (item.isPackaged()) {
            buyItem.checkPackagedQuantity();
            return;
        }
        this.buyItem.checkBulkQuantity();
    }

    public String format() {
        return item.getName() + ": " + getQuantityFormatted() + getUnitPart() + " x " + item.getPrice().format() + " --- " + totalMoney().format();
    }

    private String getUnitPart() {
        return Optional.ofNullable(item.getUnit())
                .filter(StringUtils::hasText)
                .map(u -> "(" + u + ")").orElse("");
    }

    private String getQuantityFormatted() {
        if (item.isPackaged()) {
            return buyItem.getQuantityIntPart() + "";
        }
        return buyItem.getQuantityString();
    }

    public Money totalMoney() {
        return item.calculateTotalMoney(buyItem.getQuantity());
    }
}
