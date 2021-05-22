package mob.code.supermarket.bean;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Getter
public class ReceiptItem {
    private final String name;
    private final int count;
    private final double price;
    private final String unit;

    public ReceiptItem(String name, int count, double price, String unit) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.unit = unit;
    }

    public String format() {
        String unitPart = Optional.ofNullable(this.unit)
                .filter(str -> !StringUtils.isEmpty(str))
                .map(u -> "(" + u + ")").orElse("");
        return name + ": " + count + unitPart + " x " + new Money(price) + " --- " + new Money(total());
    }

    public double total() {
        return this.count * this.price;
    }
}
