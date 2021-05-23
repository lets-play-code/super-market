package mob.code.supermarket.bean;

/**
 * @program: super-market
 * @description:
 * @author: sky wang
 * @create: 2021-05-23 10:37
 */
public class BarCodeException extends RuntimeException {

    private String barCode;

    public BarCodeException(String barCode) {
        this.barCode = barCode;
    }

    @Override
    public String getMessage() {
        return "can not recognize barcode:\n" + this.barCode;
    }
}
