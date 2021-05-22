package mob.code.supermarket.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Simon
 * @date 2021/5/22 14:26
 */
public class Receipt {

    private static final String HEADER = "****** SuperMarket receipt ******";
    private static final String TAIL = "*********************************";
    private final List<Order> orders;

    public Receipt(List<Order> orders) {

        this.orders = orders;
    }

    public List<String> print() {
        List<String> result = new ArrayList<>();
        result.add(HEADER);
        orders.forEach(order -> {
            result.add(printOrder(order));
        });
        result.add("---------------------------------");
        result.add("total: 51.90(CNY)");
        result.add(TAIL);
        return result;
    }

    private String printOrder(Order order) {
        return String.format(
                "%s: %s x %.2f --- %.2f",
                order.getName(),
                printQuantity(order),
                order.getPrice(),
                order.getAmount()
        );
    }

    private String printQuantity(Order order) {
        String unit = order.getUnit().equals("") ? "" : format("(%s)", order.getUnit());
        return format("%d", order.getQuantity()) + unit;
    }
}
