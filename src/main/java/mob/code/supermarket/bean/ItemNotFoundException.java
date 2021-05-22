package mob.code.supermarket.bean;

public class ItemNotFoundException extends RuntimeException {
    private String barcode;

    public ItemNotFoundException(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String getMessage() {
        return "item doesn't exist: " + barcode;
    }
}
