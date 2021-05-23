package mob.code.supermarket.bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mob.code.supermarket.legacy.BarcodeReader;

public class BuyItems {
    private List<String> items;

    public BuyItems(List<String> items) {
        this.items = items;
    }

    public Stream<BuyItem> toList() {
        return items.stream()
                .map(BuyItem::from)
                .collect(Collectors.groupingBy(BuyItem::getBarcode))
                .entrySet()
                .stream().map(entry -> new BuyItem(entry.getKey(), entry.getValue()));
    }

    public List<String> toNewList(List<String> items) {
       return BarcodeReader.barcodeFactory().getBarcode(String.join("\n", items));
    }
}
