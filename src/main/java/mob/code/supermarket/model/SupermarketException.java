package mob.code.supermarket.model;

import static java.lang.String.format;

public class SupermarketException extends RuntimeException {
    public SupermarketException(String message, Throwable exception) {
        super(message, exception);
    }

    public SupermarketException(String message) {
        super(message);
    }

    public SupermarketException(Throwable exception) {
        super(exception);
    }

    public static SupermarketException generateWrongQuantityException(String barcode) {
        return new SupermarketException(format("wrong quantity of %s", barcode));
    }
}
