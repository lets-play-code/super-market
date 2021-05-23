package mob.code.supermarket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    private final List<ReceiptItem> items;
    public static final String HEADER = "****** SuperMarket receipt ******";
    public static final String SEPARATOR = "---------------------------------";
    public static final String FOOTER = "*********************************";

    public Receipt(List<ReceiptItem> items) {
        this.items = items;
    }

    public Receipt() {
        this.items = new ArrayList<>();
    }

    public String[] format() {
        List<String> result = new ArrayList<>();
        result.add(HEADER);
        result.addAll(formattedItems());
        result.add(SEPARATOR);
        result.add(formattedTotal());
        result.add(FOOTER);
        return result.toArray(new String[0]);
    }

    private String formattedTotal() {
        return String.format("total: %.2f(CNY)", getTotalCost());
    }

    private List<String> formattedItems() {
        return items.stream().map(ReceiptItem::format).collect(Collectors.toList());
    }

    private double getTotalCost() {
        return items.stream()
                .mapToDouble(ReceiptItem::getCost)
                .sum();
    }

}
