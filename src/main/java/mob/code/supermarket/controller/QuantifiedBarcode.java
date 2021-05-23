package mob.code.supermarket.controller;

public class QuantifiedBarcode {

    private final String code;
    private int quantity;

    public QuantifiedBarcode(String scan) {
        code = scan.split("-")[0];
        String[] split = scan.split("-");
        quantity = 1;
        if (split.length > 1) {
            quantity = Integer.parseInt(split[1]);
        }
    }

    public QuantifiedBarcode(String code, Long quantity) {
        this.code = code;
        this.quantity = quantity.intValue();
    }

    public String getBarCode() {
        return code;
    }

    public int getQuantity() {
        return quantity;
    }
}
