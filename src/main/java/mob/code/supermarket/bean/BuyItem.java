package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BuyItem {

    private String barcode;
    private double count;
    private List<String> original;

    public BuyItem(String key, List<BuyItem> values) {
        this.barcode = key;
        this.count = values.stream().mapToDouble(BuyItem::getCount).sum();
        this.original = values.stream().flatMap(buyItem -> buyItem.original.stream())
                .collect(Collectors.toList());
    }

    public BuyItem(String barcode, double count, String original) {
        this.barcode = barcode;
        this.count = count;
        this.original = Arrays.asList(original);
    }

    public static BuyItem from(String str) {
        String[] split = str.split("-");
        if (split.length == 2) {
            return new BuyItem(split[0], Double.parseDouble(split[1]), str);
        }
        return new BuyItem(str, 1, str);
    }
}
