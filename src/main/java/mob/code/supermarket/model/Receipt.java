package mob.code.supermarket.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<ReceiptItem> items = new ArrayList<>();
    public static final String HEADER = "****** SuperMarket receipt ******";
    public static final String SEPARATOR = "---------------------------------";
    public static final String FOOTER = "*********************************";

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
        List<String> result = new ArrayList<>();
        for (ReceiptItem receiptItem : items) {
            result.add(
                    String.format("%s: %d x %.2f --- %.2f",
                            receiptItem.getName(),
                            receiptItem.getQuantity(),
                            receiptItem.getPrice(),
                            receiptItem.getCost()));
        }
        return result;
    }

    private double getTotalCost() {
        return items.stream()
                .mapToDouble(ReceiptItem::getCost)
                .sum();
    }

    public void add(ReceiptItem item) {
        items.add(item);
    }
}
