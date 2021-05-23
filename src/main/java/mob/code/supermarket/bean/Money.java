package mob.code.supermarket.bean;

import java.text.DecimalFormat;

public class Money {
    private double value;

    public Money(double value) {
        this.value = value;
    }
    public Money(String str){
        this.value = Double.parseDouble(str);
    }
    public String format() {
        return new DecimalFormat("0.00").format(Math.floor(value * 100) / 100);
    }

    public double toActual() {
        return Double.parseDouble(format());
    }
}
