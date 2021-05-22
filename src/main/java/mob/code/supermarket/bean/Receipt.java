package mob.code.supermarket.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Receipt {
    private String header = "****** SuperMarket receipt ******";
    private String footer = "*********************************";
    private String split = "---------------------------------";
    private List<ReceiptItem> items;

    public Receipt(List<ReceiptItem> items) {

        this.items = items;
    }

    public static Receipt of(List<ReceiptItem> items) {
        return new Receipt(items);
    }

    public List<String> output() {
        ArrayList<String> result = new ArrayList<>();
        result.add(header);
        result.addAll(getBuyItems());
        result.add(split);
        result.add(getTotalString());
        result.add(footer);
        return result;
    }

    private List<String> getBuyItems() {
        return items.stream()
                .map(ReceiptItem::format)
                .collect(Collectors.toList());
    }

    private String getTotalString() {
        return "total: " + new Money(getTotalPrice()) + "(CNY)";
    }

    private double getTotalPrice() {
        return items.stream()
                .mapToDouble(ReceiptItem::total)
                .sum();
    }
}
