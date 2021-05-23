package mob.code.supermarket.bean;

import mob.code.supermarket.bean.Item;

import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findByBarcode(String barcode);

    void save(Item item);
}
