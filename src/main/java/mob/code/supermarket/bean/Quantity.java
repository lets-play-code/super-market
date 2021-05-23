package mob.code.supermarket.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Quantity {
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
        if (value - (int) value > 0.000001) {
            throw new WrongQuantityException(barcode);
        }
    }

    public void assertLegal(String barcode) {
        this.ensureIsInteger(this.value * 10, barcode);
    }

    public void ensureNotZero(String barcode) {
        if(value<0.000001){
            throw new WrongQuantityException(barcode);
        }
    }
}
