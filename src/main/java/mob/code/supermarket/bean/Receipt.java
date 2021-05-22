package mob.code.supermarket.bean;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        String formattedPrice = total.setScale(2).toString();
        return Arrays.asList(header, split, "total: " + formattedPrice + "(CNY)", footer);
    }

    private double getTotalPrice() {
        return 0;
    }
}
