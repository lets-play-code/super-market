package mob.code.supermarket.service;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.dao.BarcodeAndCount;
import mob.code.supermarket.dao.ItemRepository;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * description: ItemService <br>
 * date: 2021/5/22 下午2:46 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private Order makeOrder(BarcodeAndCount barcodeAndCount) {
        Optional<Item> items = itemRepository.getByBarcode(barcodeAndCount.getBarcode());
        Item item = items.orElseThrow(() -> new SupermarketException(format("item doesn't exist: %s", barcodeAndCount.getInbarcode())));
        return Order.create(item, barcodeAndCount.getCount());
    }

    public BarcodeAndCount parseBarcode(String barcode) {
        String[] split = barcode.split("-");
        if (split.length == 1) {
            return new BarcodeAndCount(barcode, 1,barcode);
        } else {
            return new BarcodeAndCount(split[0], Integer.parseInt(split[1]),barcode);
        }
    }

    public List<Order> makeOrders(List<String> barcodes) {
        final List<BarcodeAndCount> barcodeAndCountList =new ArrayList<>();
        barcodes.stream()
                .map(this::parseBarcode)
                .collect(Collectors.groupingBy(BarcodeAndCount::getBarcode))
                .forEach((key, value) -> {
                    BarcodeAndCount barcodeAndCount = value.get(0);
                    barcodeAndCount.setCount(value.size());
                    barcodeAndCountList.add(barcodeAndCount);
                });
        return barcodeAndCountList.stream()
                .map(this::makeOrder)
                .collect(Collectors.toList());
    }
}
