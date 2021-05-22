package mob.code.supermarket.bean;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuyItems {
    private List<String> items;

    public BuyItems(List<String> items) {
        this.items = items;
    }

    public Stream<BuyItem> toList() {
        return items.stream()
                .map(BuyItem::from)
                .collect(Collectors.groupingBy(BuyItem::getBarcode, Collectors.summingInt(BuyItem::getCount)))
                .entrySet()
                .stream().map(entry -> new BuyItem(entry.getKey(), entry.getValue()));
    }
}
