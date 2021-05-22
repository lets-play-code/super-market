package mob.code.supermarket.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    public static final String SUPER_MARKET_RECEIPT = "****** SuperMarket receipt ******";
    public static final String FOOTER = "*********************************";
    public static final String SPLITTER = "---------------------------------";
    private final List<ReceiptItem> items = new ArrayList<>();

    public List<String> toResult() {
        ArrayList<String> result = new ArrayList<>();
        result.add(SUPER_MARKET_RECEIPT);
        result.addAll(getItemsString());
        result.add(SPLITTER);
        result.add(getTotalString());
        result.add(FOOTER);
        return result;
    }

    private String getTotalString() {
        BigDecimal reduce = items.stream()
                .map(ReceiptItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return String.format("total: %.2f(CNY)", reduce);
    }

    private List<String> getItemsString() {
        return items.stream()
                .map(ReceiptItem::itemInfo)
                .collect(Collectors.toList());
    }

    public void add(ReceiptItem receiptItem) {
        items.add(receiptItem);
    }

    public void addAll(List<ReceiptItem> receiptItems) {
        items.addAll(receiptItems);
    }
}
