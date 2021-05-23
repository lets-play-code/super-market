package mob.code.supermarket.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price {

    private final double value;

    public Price(double value) {
        this.value = value;
    }

    public String toString() {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
    }
}
