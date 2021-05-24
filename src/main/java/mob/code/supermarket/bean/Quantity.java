package mob.code.supermarket.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode
public class Quantity {
    public static final double DOUBLE_MIN = 0.000001;
    private final BigDecimal value;

    public Quantity(BigDecimal valueBig) {
        this.value = valueBig;
    }

    public Quantity add(Quantity another) {
        return new Quantity(this.value.add(another.getValue()));
    }

    public Quantity(String str) {
        this.value = new BigDecimal(str);
    }

    public String toString() {
        return value.setScale(1, RoundingMode.HALF_UP).toString();
    }

    private boolean isZero(double value) {
        return value < DOUBLE_MIN;
    }

    public boolean isZero() {
        return this.isZero(value.doubleValue());
    }

    public boolean onlyFraction(int count) {
        BigDecimal decimal = this.value.multiply(new BigDecimal(10).pow(count));
        double fractional = decimal.doubleValue() - decimal.intValue();
        return isZero(fractional);
    }

    public int toInt() {
        return value.intValue();
    }
}
