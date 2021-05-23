package mob.code.supermarket.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    private String header = "****** SuperMarket receipt ******";
    private String footer = "*********************************";
    private String separator = "---------------------------------";

    private List<ReceiptItem> items = new ArrayList<>();

    public void add(ReceiptItem receiptItem) {
        this.items.add(receiptItem);
    }

    public List<String> print() {
        List<String> result = new ArrayList<>();
        result.add(header);
        result.addAll(itemsPrint());
        result.add(separator);
        result.add(totalPrint());
        result.add(footer);
        return result;
    }

    private String totalPrint() {
        return "total: " + new Price(getTotalPrice()) + "(CNY)";
    }

    private double getTotalPrice() {
        return this.items.stream()
                .mapToDouble(ReceiptItem::totalAmount)
                .sum();
    }

    private List<String> itemsPrint() {
        return this.items.stream()
                .map(ReceiptItem::format)
                .collect(Collectors.toList());
    }
}
