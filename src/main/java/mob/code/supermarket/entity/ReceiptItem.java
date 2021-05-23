package mob.code.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ReceiptItem {
    private String name;
    private Float count;
    private BigDecimal price;
    private String unit;
    private String type;

    public String itemInfo() {
        BigDecimal total = totalPrice();
        if (isIndividual() || isCountInteger()) {
            if (StringUtils.isEmpty(unit)) {
                return String.format("%s: %.0f x %.2f --- %.2f", getName(), getCount(), price, total);
            }
            return String.format("%s: %.0f(%s) x %.2f --- %.2f", getName(), getCount(), unit, price, total);
        }
        if (StringUtils.isEmpty(unit)) {
            return String.format("%s: %.1f x %.2f --- %.2f", getName(), getCount(), price, total);
        }
        return String.format("%s: %.1f(%s) x %.2f --- %.2f", getName(), getCount(), unit, price, total);
    }

    private boolean isIndividual() {
        return "individual".contentEquals(type);
    }

    public boolean isCountInteger() {
        return (this.count - this.count.intValue()) == 0;
    }


    public BigDecimal totalPrice() {
        return price.multiply(BigDecimal.valueOf(count));
    }
}
