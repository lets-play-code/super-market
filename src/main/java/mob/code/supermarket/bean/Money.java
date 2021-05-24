package mob.code.supermarket.bean;

import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Money {
    @Getter
    private BigDecimal value;

    public Money(String str) {
        this.value = new BigDecimal(str);
    }

    public Money toMoney() {
        return new Money(format());
    }

    public Money(BigDecimal bigDecimal) {
        this.value = bigDecimal;
    }

    public static Money zero() {
        return new Money(new BigDecimal(0));
    }

    public String format() {
        return new DecimalFormat("0.00").format(Math.floor(value.doubleValue() * 100) / 100);
    }

    public double toActual() {
        return Double.parseDouble(format());
    }

    public Money times(Quantity quantity) {
        return new Money(this.value.multiply(quantity.getValue()));
    }

    public Money add(Money another) {
        return new Money(this.value.add(another.value));
    }
}
