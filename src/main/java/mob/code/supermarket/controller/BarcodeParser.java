package mob.code.supermarket.controller;

public class BarcodeParser {

    private final String barcode;
    private int quantity;

    public BarcodeParser(String scan) {
        barcode = scan.split("-")[0];
        String[] split = scan.split("-");
        quantity = 1;
        if (split.length > 1) {
            quantity = Integer.parseInt(split[1]);
        }
    }

    public String getBarCode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }
}
