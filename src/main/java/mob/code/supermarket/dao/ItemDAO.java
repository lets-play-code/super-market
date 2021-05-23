package mob.code.supermarket.dao;

import org.springframework.data.repository.CrudRepository;


public interface ItemDAO extends CrudRepository<ItemDO, String> {
}
