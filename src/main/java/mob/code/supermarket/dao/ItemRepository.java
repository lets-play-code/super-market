package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;

import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findByBarcode(String barcode);
}
