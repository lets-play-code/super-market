package mob.code.supermarket.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Quantity {
    private double value;

    public Quantity(double value) {
        this.value = value;
    }

    public String toString(){
        return BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).toString();
    }
}
