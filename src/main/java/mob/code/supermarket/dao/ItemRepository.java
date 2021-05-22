package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, String> {
    Optional<Item> getByBarcode(String barcode);
}
