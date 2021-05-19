package mob.code.supermarket.model;

import mob.code.supermarket.bean.Item;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    public static final String HEAD = "****** SuperMarket receipt ******";
    public static final String FOOT = "*********************************";
    public static final String SEPARATOR = "---------------------------------";

    private final List<OrderItem> orderItems = new ArrayList<>();

    public List<String> output() {
        List<String> result = new ArrayList<>();
        result.add(HEAD);
        for (OrderItem orderItem : orderItems) {
            result.add(formatPurchase(orderItem));
        }
        result.add(SEPARATOR);
        result.add(String.format("total: %.2f(CNY)", getTotal()));
        result.add(FOOT);
        return result;
    }

    private String formatPurchase(OrderItem orderItem) {
        Item item = orderItem.getItem();
        String unit = item.getUnit().isEmpty() ? "" : "("+item.getUnit()+")";
        return String.format("%s: %d%s x %.2f --- %.2f",
                item.getName(), orderItem.getCount(), unit, item.getPrice(), orderItem.getSubTotal());
    }

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public double getTotal() {
        double result = 0;
        for (OrderItem orderItem : orderItems) {
            result += orderItem.getSubTotal();
        }
        return result;
    }

}
