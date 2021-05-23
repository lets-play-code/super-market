package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.ItemRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private ItemDAO itemDao;

    public ItemRepositoryImpl(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public Optional<Item> findByBarcode(String barcode) {
        return itemDao.findById(barcode).map(ItemDO::toEntity);
    }

    @Override
    public void save(Item item) {
        itemDao.save(ItemDO.fromEntity(item));
    }
}
