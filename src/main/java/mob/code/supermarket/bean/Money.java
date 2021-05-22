package mob.code.supermarket.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private double value;

    public Money(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
    }
}
