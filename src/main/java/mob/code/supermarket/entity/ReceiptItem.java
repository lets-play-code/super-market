package mob.code.supermarket.entity;

import lombok.Data;
import mob.code.supermarket.bean.Item;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class ReceiptItem {
    private Item item;
    private Float count;

    public ReceiptItem(Item item, Float count) {
        this.item = item;
        this.count = count;
    }

    public ReceiptItem(String name, Float count, BigDecimal price, String unit, String type) {
        this.item = new Item("", name, unit, price.doubleValue(), type);
        this.count = count;
    }

    public String itemInfo() {
        BigDecimal total = totalPrice();
        if (item.isIndividual() || isCountInteger()) {
            if (StringUtils.isEmpty(item.getUnit())) {
                return String.format("%s: %.0f x %.2f --- %.2f", item.getName(), count, item.getPrice(), total);
            }
            return String.format("%s: %.0f(%s) x %.2f --- %.2f", item.getName(), count, item.getUnit(), item.getPrice(), total);
        }
        if (StringUtils.isEmpty(item.getUnit())) {
            return String.format("%s: %.1f x %.2f --- %.2f", item.getName(), count, item.getPrice(), total);
        }
        return String.format("%s: %.1f(%s) x %.2f --- %.2f", item.getName(), count, item.getUnit(), item.getPrice(), total);
    }

    public boolean isCountInteger() {
        return count - count.intValue() == 0;
    }

    public BigDecimal totalPrice() {
        BigDecimal price = BigDecimal.valueOf(item.getPrice());
        BigDecimal multiply = price.multiply(BigDecimal.valueOf(count));
        return multiply.setScale(2, RoundingMode.DOWN);
    }
}
