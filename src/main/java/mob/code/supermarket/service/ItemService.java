package mob.code.supermarket.service;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.dao.BarcodeAndCount;
import mob.code.supermarket.dao.ItemRepository;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BarcodeAndCount parseBarcode(String inBarcode) {
        String[] split = inBarcode.split("-");
        if (split.length == 1) {
            return new BarcodeAndCount(inBarcode, 1,inBarcode);
        }
        String barcode = split[0];
        String quantity = split[1];
        validateQuantity(quantity, barcode);
        return new BarcodeAndCount(barcode, new BigDecimal(quantity),inBarcode);
    }

    private void validateQuantity(String quantity, String barcode) {
        if(new BigDecimal(quantity).setScale(1, RoundingMode.FLOOR).compareTo(new BigDecimal(quantity)) != 0) {
            throw new SupermarketException(format("wrong quantity of %s", barcode));
        }
        if(new BigDecimal(quantity).compareTo(BigDecimal.ZERO) == 0) {
            throw new SupermarketException(format("wrong quantity of %s", barcode));
        }
    }

    public List<Order> makeOrders(List<String> barcodes) {
        return parseBarcodes(barcodes).stream()
                .map(this::makeOrder)
                .collect(Collectors.toList());
    }

    private List<BarcodeAndCount> parseBarcodes(List<String> barcodes) {
        return barcodes.stream()
                .map(this::parseBarcode)
                .collect(Collectors.groupingBy(BarcodeAndCount::getBarcode))
                .values().stream()
                .map(this::reduceDuplicateOrders)
                .collect(Collectors.toList());
    }

    private BarcodeAndCount reduceDuplicateOrders(List<BarcodeAndCount> duplicateOrders) {
        BarcodeAndCount reducedOrder = new BarcodeAndCount();
        BeanUtils.copyProperties(duplicateOrders.get(0), reducedOrder);
        reducedOrder.setCount(duplicateOrders.stream()
                .map(BarcodeAndCount::getCount).reduce(BigDecimal.ZERO,BigDecimal::add));

        return reducedOrder;
    }
}
