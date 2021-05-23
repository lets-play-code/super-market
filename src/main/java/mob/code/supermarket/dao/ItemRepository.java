package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Optional<Item> getByBarcode(String barcode);
}
