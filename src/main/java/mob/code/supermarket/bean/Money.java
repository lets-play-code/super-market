package mob.code.supermarket.bean;

import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Money {
    @Getter
    private BigDecimal valueBig;

    public Money(double value) {
        this.valueBig = new BigDecimal(value);
    }

    public Money(String str) {
        this.valueBig = new BigDecimal(str);
    }

    public Money toMoney() {
        return new Money(format());
    }

    public Money(BigDecimal bigDecimal) {
        this.valueBig = bigDecimal;
    }

    public String format() {
        return new DecimalFormat("0.00").format(Math.floor(valueBig.doubleValue() * 100) / 100);
    }

    public double toActual() {
        return Double.parseDouble(format());
    }

    public Money times(Quantity quantity) {
        return new Money(this.valueBig.multiply(quantity.getValue()));
    }

    public Money add(Money another) {
        return new Money(this.valueBig.add(another.valueBig));
    }
}
