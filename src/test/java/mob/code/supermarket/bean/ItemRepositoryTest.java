package mob.code.supermarket.bean;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ItemRepositoryTest {

    @Test
    public void should_return_receipt_item_by_barcode() {
        ProductRepository productRepository = new DefaultProductRepository();
        productRepository.add(new Product("12345678","pizza" ,"", 15.00,""));
        Product product = productRepository.findByBarcode("12345678");
        assertThat(product,is(new Product("12345678", "pizza", "", 15.00,"")));
    }
}