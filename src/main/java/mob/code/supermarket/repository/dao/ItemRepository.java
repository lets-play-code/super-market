package mob.code.supermarket.repository.dao;

import mob.code.supermarket.repository.entity.EItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * description: ItemRepository <br>
 * date: 2021/5/22 下午11:55 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@Repository
public interface  ItemRepository extends JpaRepository<EItem, Long> {

    @Query("select u from EItem u where u.barcode = ?1")
    EItem findByBarcode(String barcode);
}
