package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.ItemRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private ItemDao itemDao;

    public ItemRepositoryImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public Optional<Item> findByBarcode(String barcode) {
        return itemDao.getSampleItems().stream().filter(item -> item.getBarcode().equals(barcode))
                .findFirst();
    }
}
