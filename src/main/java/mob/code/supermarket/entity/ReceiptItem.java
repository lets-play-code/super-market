package mob.code.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ReceiptItem {
    private String name;
    private Integer count;
    private BigDecimal price;

    public String itemInfo() {
        BigDecimal total = totalPrice();
        return String.format("%s: %d x %s --- %s", getName(), getCount(), price.toString(), total);
    }

    public BigDecimal totalPrice() {
        return price.multiply(BigDecimal.valueOf(count));
    }
}
