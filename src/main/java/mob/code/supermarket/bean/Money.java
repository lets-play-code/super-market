package mob.code.supermarket.bean;

import lombok.Getter;

import java.text.DecimalFormat;

public class Money {
    @Getter
    private double value;

    public Money(double value) {
        this.value = value;
    }

    public Money(String str) {
        this.value = Double.parseDouble(str);
    }

    public Money add(Money another) {
        return new Money(this.value + another.value);
    }

    public String format() {
        return new DecimalFormat("0.00").format(Math.floor(value * 100) / 100);
    }

    public double toActual() {
        return Double.parseDouble(format());
    }

    public Money times(Quantity quantity) {
        return new Money(this.value * quantity.getValue());
    }
}
