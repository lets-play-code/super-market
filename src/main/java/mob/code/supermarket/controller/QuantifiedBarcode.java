package mob.code.supermarket.controller;

public class QuantifiedBarcode {

    private final String code;
    private double quantity;

    public QuantifiedBarcode(String scan) {
        code = scan.split("-")[0];
        String[] split = scan.split("-");
        quantity = 1;
        if (split.length > 1) {
            quantity = Double.parseDouble(split[1]);
        }
    }

    public QuantifiedBarcode(String code, double quantity) {
        this.code = code;
        this.quantity = quantity;
    }

    public String getBarCode() {
        return code;
    }

    public double getQuantity() {
        return quantity;
    }
}
