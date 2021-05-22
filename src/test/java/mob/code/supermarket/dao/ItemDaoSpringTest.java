package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.service.ItemService;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * description: ItemDaoTest <br>
 * date: 2021/5/22 下午12:06 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemDaoSpringTest {
    @Autowired
    ItemDao itemDao;

    @Autowired
    ItemService itemService;


    //@Test
    public void given_barcode_then_item() {
        List<Item> sampleItems = itemDao.getSampleItems();
        int size = sampleItems.size();
        System.out.println(size);
        String barcode = "12345678";
        Optional<Item> item = itemDao.getItem(barcode);
        assertThat(item.get().getBarcode(), is(barcode));

    }

    @Test
    public void given_barcode_then_barcode_1() {
        String barcode = "12345678";
//        String barcode1 = "12345678-3";
        BarcodeAndCount barcodeAndCount = itemDao.parseBarcode(barcode);
        assertThat(barcodeAndCount.getBarcode(),is(barcode));
        assertThat(barcodeAndCount.getCount(),is(1));
    }
    @Test
    public void given_barcode_then_barcode_3() {
        String barcode = "12345678";
        int count=3;
        BarcodeAndCount barcodeAndCount = itemDao.parseBarcode(barcode+"-"+count);
        assertThat(barcodeAndCount.getBarcode(),is(barcode));
        assertThat(barcodeAndCount.getCount(),is(count));
    }

    @Test
    public void given_barcodelist_then_orderlist() {

    }
}