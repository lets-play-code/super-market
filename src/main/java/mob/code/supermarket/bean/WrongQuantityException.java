package mob.code.supermarket.bean;

public class WrongQuantityException extends RuntimeException {
    private String barcode;


    public WrongQuantityException(String barcode) {
        this.barcode =barcode;
    }
}
