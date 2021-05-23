package mob.code.supermarket.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode
public class Quantity {
    public static final double DOUBLE_MIN = 0.000001;
    private BigDecimal value;

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

    public void assertIsInteger(String barcode) {
        ensureIsInteger(this.value.doubleValue(), barcode);
    }

    private void ensureIsInteger(double value, String barcode) {
        double valueTimes10 = value - (int) value;
        if (!isZero(valueTimes10)) {
            throw new WrongQuantityException(barcode);
        }
    }

    private boolean isZero(double value) {
        return value < DOUBLE_MIN;
    }

    public void assertBulkQuantityLegal(String barcode) {
        this.ensureIsInteger(this.value.doubleValue() * 10, barcode);
    }

    public void ensureNotZero(String barcode) {
        if (isZero(value.doubleValue())) {
            throw new WrongQuantityException(barcode);
        }
    }

    public int toInt() {
        return (int) value.doubleValue();
    }
}
