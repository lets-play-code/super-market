package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BuyItem {
    private final String barcode;
    private final double quantity;
    private final List<String> original;

    public BuyItem(String key, List<BuyItem> values) {
        this.barcode = key;
        this.quantity = values.stream().mapToDouble(BuyItem::getQuantity).sum();
        this.original = values.stream()
                .flatMap(BuyItem::originalStream)
                .collect(Collectors.toList());
    }

    private Stream<String> originalStream() {
        return original.stream();
    }


    public BuyItem(String barcode, double count, String original) {
        this.barcode = barcode;
        this.quantity = count;
        this.original = Collections.singletonList(original);
    }

    public static BuyItem from(String str) {
        String[] split = str.split("-");
        if (split.length == 2) {
            return new BuyItem(split[0], Double.parseDouble(split[1]), str);
        }
        return new BuyItem(str, 1, str);
    }

    public boolean hasQuantity() {
        return this.originalStream().allMatch(record -> record.contains("-"));
    }

}
