package mob.code.supermarket.bean;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

public class ReceiptItem {
    private final String name;
    private final int count;
    private final String unit;
    private final double price;

    public ReceiptItem(String name, int count, String unit, double price) {
        this.name = name;
        this.count = count;
        this.unit = unit;
        this.price = price;
    }

    public String format() {
        String unitPart = Optional.ofNullable(this.unit)
                .filter(str -> !StringUtils.isEmpty(str))
                .map(u -> "(" + u + ")").orElse("");
        return this.name + ": " + this.count + unitPart + " x " + new Price(price) + " --- " + new Price(totalAmount());
    }

    public double totalAmount() {
        return this.price * this.count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return count == that.count && Double.compare(that.price, price) == 0 && Objects.equals(name, that.name) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count, unit, price);
    }
}
