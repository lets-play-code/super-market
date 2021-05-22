package mob.code.supermarket.repository.dao;

import com.alibaba.fastjson.JSONObject;
import mob.code.supermarket.repository.entity.EItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * description: ItemRepositoryTest <br>
 * date: 2021/5/23 上午12:26 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTest {
@Autowired
ItemRepository itemRepository;
    @Test
    public void findall() {
        List<EItem> all = itemRepository.findAll();
        System.out.println(JSONObject.toJSONString(all));
    }

    @Test
    public void findByBarcode() {
        String barcode = "12345678";
        EItem byBarcode = itemRepository.findByBarcode(barcode);
        assertThat(byBarcode.getBarcode(),is(barcode));
    }
}