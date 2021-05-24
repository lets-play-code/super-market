package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BuyItem {
    private final String barcode;
    private final List<String> original;
    private final Quantity quantity;

    public BuyItem(String key, List<BuyItem> values) {
        this.barcode = key;
        this.quantity = values.stream().map(BuyItem::getQuantity).reduce(new Quantity(new BigDecimal(0)), Quantity::add);
        this.original = values.stream()
                .flatMap(BuyItem::originalStream)
                .collect(Collectors.toList());
    }

    private Stream<String> originalStream() {
        return original.stream();
    }

    public BuyItem(String barcode, String count, String original) {
        this(barcode, count, Collections.singletonList(original));
    }

    public BuyItem(String barcode, String quantity, List<String> original) {
        this.barcode = barcode;
        this.quantity = new Quantity(quantity);
        this.original = original;
    }

    public static BuyItem from(String str) {
        String[] split = str.split("-");
        if (split.length == 2) {
            return new BuyItem(split[0], split[1], str);
        }
        return new BuyItem(str, "1", str);
    }

    public boolean hasQuantity() {
        return this.originalStream().allMatch(record -> record.contains("-"));
    }

    public int getQuantityIntPart() {
        return quantity.toInt();
    }

    public String getQuantityString() {
        return quantity.toString();
    }

    public void checkBulkQuantity() {
        if (!this.hasQuantity()) {
            throw new WrongQuantityException(this.getBarcode());
        }
        quantity.assertBulkQuantityLegal(this.getBarcode());
    }

    public void checkPackagedQuantity() {
        quantity.assertIsInteger(getBarcode());
    }
}
