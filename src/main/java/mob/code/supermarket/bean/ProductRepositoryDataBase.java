package mob.code.supermarket.bean;

import mob.code.supermarket.dao.ItemDAO;
import mob.code.supermarket.dao.ItemDO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductRepositoryDataBase implements ProductRepository{
    private ItemDAO itemDao;

    public ProductRepositoryDataBase(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public void add(Product receiptItem) {

    }

    @Override
    public Product findByBarcode(String barcode) {

        Optional<Item> optionalItem =  itemDao.findById(barcode).map(ItemDO::toEntity);
        Item item = optionalItem.orElseThrow(() -> new ItemNotFoundException(barcode));
        return new Product(item.getBarcode(),item.getName(),item.getUnit(),item.getPrice(),item.getType());
    }

    @Override
    public void clear() {

    }
}
