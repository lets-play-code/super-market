package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mob.code.supermarket.dao.BarcodeAndCount;
import mob.code.supermarket.model.SupermarketException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static mob.code.supermarket.model.SupermarketException.generateWrongQuantityException;

/**
 * @author Simon
 * @date 2021/5/22 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String barcode;
    private String name;
    private String unit;
    private BigDecimal price;
    private String type;
    private BigDecimal quantity;

    public static Order create(Item item, BigDecimal quantity) {
        return new Order(item.getBarcode(), item.getName(), item.getUnit(),
                BigDecimal.valueOf(item.getPrice()), item.getType(), quantity);

    }

    public static Order create(Item item, BarcodeAndCount barcodeAndCount) {

        return new Order(item.getBarcode(), item.getName(), item.getUnit(),
                BigDecimal.valueOf(item.getPrice()), item.getType(), barcodeAndCount.getCount());

    }


    public BigDecimal getAmount() {
        return quantity.multiply(price)
                .setScale(2, RoundingMode.FLOOR);
    }

    public void validateOrderQuantity(String inbarcode) {
        if (isPackage() && this.quantity.remainder(ONE).compareTo(ZERO) != 0) {
            throw generateWrongQuantityException(this.barcode);
        }
        if (!isPackage() && !inbarcode.contains("-")) {
            throw generateWrongQuantityException(this.barcode);
        }
    }

    private boolean isPackage() {
        return ItemTypeEnum.of(type).isPackage();
    }
}
