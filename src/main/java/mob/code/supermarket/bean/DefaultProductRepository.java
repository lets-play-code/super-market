package mob.code.supermarket.bean;

import java.util.*;

public class DefaultProductRepository implements ProductRepository {
    private Map<String, Product> products = new HashMap<>();

    @Override
    public void add(Product product) {
        this.products.put(product.barcode(), product);
    }

    @Override
    public Product findByBarcode(String barcode){
        return products.get(barcode);
    }

    public void clear() {
        this.products.clear();
    }
}
