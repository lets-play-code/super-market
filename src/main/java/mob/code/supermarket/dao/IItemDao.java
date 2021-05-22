package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;

import java.util.List;

public interface IItemDao {
    public List<Item> getSampleItems();

    public List<Item> getItems(List<String> codes);
}
