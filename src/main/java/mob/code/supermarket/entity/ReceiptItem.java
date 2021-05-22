package mob.code.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ReceiptItem {
    private String name;
    private Integer count;
    private BigDecimal price;
    private String unit;

    public String itemInfo() {
        BigDecimal total = totalPrice();
        if (StringUtils.isEmpty(unit)) {
            return String.format("%s: %d x %.2f --- %.2f", getName(), getCount(), price, total);
        }
        return String.format("%s: %d(%s) x %.2f --- %.2f", getName(), getCount(), unit, price, total);
    }


    public BigDecimal totalPrice() {
        return price.multiply(BigDecimal.valueOf(count));
    }
}
