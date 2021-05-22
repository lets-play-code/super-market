package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;

import java.util.List;

public class ItemMock implements IItemDao {

    @Override
    public List<Item> getSampleItems() {
        return null;
    }

    @Override
    public List<Item> getItems(List<String> codes) {
        return null;
    }
}
