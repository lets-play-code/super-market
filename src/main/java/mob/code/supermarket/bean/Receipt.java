package mob.code.supermarket.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
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
        BigDecimal total = new BigDecimal(getTotalPrice());
        String formattedPrice = total.setScale(2, RoundingMode.HALF_UP).toString();
        List<String> itemsString = items.stream()
                .map(ReceiptItem::format)
                .collect(Collectors.toList());
        Stream<String> concat = Stream.concat(Stream.of(header), itemsString.stream());
        return Stream.concat(concat, Stream.of(split, "total: " + formattedPrice + "(CNY)", footer)).collect(Collectors.toList());
    }

    private double getTotalPrice() {
        return items.stream()
                .mapToDouble(ReceiptItem::total)
                .sum();
    }
}
