package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Simon
 * @date 2021/5/22 14:38
 */
@Data
@AllArgsConstructor
public class Order {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private String type;
    private int quantity;
    private String message;
    private String inBarcode;
}
