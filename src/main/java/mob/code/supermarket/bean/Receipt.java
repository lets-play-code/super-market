package mob.code.supermarket.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    private final static String HEADER = "****** SuperMarket receipt ******";
    private final static String FOOTER = "*********************************";
    private final static String SPLIT = "---------------------------------";
    private final List<ReceiptItem> items;

    public Receipt(List<ReceiptItem> items) {
        this.items = items;
    }

    public static Receipt of(List<ReceiptItem> items) {
        return new Receipt(items);
    }

    public List<String> output() {
        ArrayList<String> result = new ArrayList<>();
        result.add(HEADER);
        result.addAll(getBuyItems());
        result.add(SPLIT);
        result.add(getTotalString());
        result.add(FOOTER);
        return result;
    }

    private List<String> getBuyItems() {
        return items.stream()
                .map(ReceiptItem::format)
                .collect(Collectors.toList());
    }

    private String getTotalString() {
        return "total: " + getTotalPriceMoney().format() + "(CNY)";
    }

    private Money getTotalPriceMoney() {
        return items.stream()
                .map(ReceiptItem::totalMoney)
                .map(Money::toMoney)
                .reduce(Money.zero(), Money::add);
    }
}
