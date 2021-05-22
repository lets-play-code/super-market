package mob.code.supermarket.service;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.dao.BarcodeAndCount;
import mob.code.supermarket.dao.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description: ItemService <br>
 * date: 2021/5/22 下午2:46 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@Service
public class ItemService {

    @Autowired
    ItemDao itemDao;

    public Order makeOrder(String inbarcode) {
        BarcodeAndCount barcodeAndCount = parseBarcode(inbarcode);
        Optional<Item> item = itemDao.getItem(barcodeAndCount.getBarcode());
        if (item.isPresent())
          return  new Order(item.get().getBarcode(), item.get().getName(), item.get().getUnit(), item.get().getPrice(), "", barcodeAndCount.getCount(), "", inbarcode);
       else
        return new Order("", "", "", 0d, "", 0, "not find", "");
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
        return barcodes.stream().map(this::makeOrder).collect(Collectors.toList());
    }
}
