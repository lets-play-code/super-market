package mob.code.supermarket.bean;

public class WrongQuantityException extends RuntimeException {
    private String barcode;


    public WrongQuantityException(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String getMessage() {
        return "wrong quantity of " + this.barcode;
    }
}
