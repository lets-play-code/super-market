package mob.code.supermarket.entity;

import lombok.Data;
import mob.code.supermarket.bean.Item;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class ReceiptItem {
    private Item item;
    private Barcode barcode;

    public ReceiptItem(Item item, Barcode barcode) {
        this.item = item;
        this.barcode = barcode;
        checkCount();
    }

    public ReceiptItem(String name, Float count, BigDecimal price, String unit, String type) {
        this.item = new Item("", name, unit, price.doubleValue(), type);
        this.barcode = new Barcode("", count, false);
        checkCount();
    }

    private void checkCount() {
        if (!isIndividual() && barcode.isNumberUndefined()) {
            throw new SupermarketException("wrong quantity of " + barcode.getCode());
        }

        // 不能有小数，必须是整数
        if (isIndividual() && !isCountInteger()) {
            throw new SupermarketException("wrong quantity of " + barcode.getCode());
        }
    }

    public String itemInfo() {
        BigDecimal total = totalPrice();
        if (isIndividual() || isCountInteger()) {
            if (StringUtils.isEmpty(item.getUnit())) {
                return String.format("%s: %.0f x %.2f --- %.2f", item.getName(), barcode.getNumber(), item.getPrice(), total);
            }
            return String.format("%s: %.0f(%s) x %.2f --- %.2f", item.getName(), barcode.getNumber(), item.getUnit(), item.getPrice(), total);
        }
        if (StringUtils.isEmpty(item.getUnit())) {
            return String.format("%s: %.1f x %.2f --- %.2f", item.getName(), barcode.getNumber(), item.getPrice(), total);
        }
        return String.format("%s: %.1f(%s) x %.2f --- %.2f", item.getName(), barcode.getNumber(), item.getUnit(), item.getPrice(), total);
    }

    private boolean isIndividual() {
        return "individual".contentEquals(item.getType());
    }

    public boolean isCountInteger() {
        return (this.barcode.getNumber() - this.barcode.getNumber().intValue()) == 0;
    }

    public BigDecimal totalPrice() {
        BigDecimal price = BigDecimal.valueOf(item.getPrice());
        BigDecimal count = BigDecimal.valueOf(barcode.getNumber());
        BigDecimal multiply = price.multiply(count);
        return multiply.setScale(2, RoundingMode.DOWN);
    }
}
