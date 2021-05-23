package mob.code.supermarket.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Quantity {
    public static final double DOUBLE_MIN = 0.000001;
    private double value;

    public Quantity(double value) {
        this.value = value;
    }

    public String toString() {
        return BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).toString();
    }

    public void assertIsInteger(String barcode) {
        ensureIsInteger(this.value, barcode);
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

    public void assertLegal(String barcode) {
        this.ensureIsInteger(this.value * 10, barcode);
    }

    public void ensureNotZero(String barcode) {
        if (isZero(value)) {
            throw new WrongQuantityException(barcode);
        }
    }
}