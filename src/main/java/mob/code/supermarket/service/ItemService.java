package mob.code.supermarket.service;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.dao.BarcodeAndCount;
import mob.code.supermarket.dao.ItemDao;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.stereotype.Service;

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

    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public Order makeOrder(String inbarcode) {
        BarcodeAndCount barcodeAndCount = parseBarcode(inbarcode);
        Optional<Item> items = itemDao.getItem(barcodeAndCount.getBarcode());
        Item item = items.orElseThrow(() -> new SupermarketException(format("item doesn't exist: %s", inbarcode)));
        return Order.create(item, barcodeAndCount.getCount());
    }

    public BarcodeAndCount parseBarcode(String barcode) {
        String[] split = barcode.split("-");
        if (split.length == 1) {
            return new BarcodeAndCount(barcode, 1);
        } else {
            return new BarcodeAndCount(split[0], Integer.parseInt(split[1]));
        }
    }

    public List<Order> makeOrders(List<String> barcodes) {
        return barcodes.stream()
                .map(this::makeOrder)
                .collect(Collectors.toList());
    }
}
