package mob.code.supermarket.bean;

import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findByBarcode(String barcode);
}
