package mob.code.supermarket.bean;


public interface ProductRepository {

    public void add(Product receiptItem);

    public Product findByBarcode(String barcode);

    void clear();
}
