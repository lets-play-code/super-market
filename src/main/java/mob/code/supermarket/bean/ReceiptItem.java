package mob.code.supermarket.bean;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        String unitPart = Optional.ofNullable(this.unit).map(u -> "(" + u + ")").orElse("");
        return name + ": " + count + unitPart + " x " + new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).toString() + " --- " + new BigDecimal(this.total()).setScale(2,RoundingMode.HALF_UP).toString();
    }

    public double total() {
        return this.count * this.price;
    }
}
