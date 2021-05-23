package mob.code.supermarket.bean;


import java.util.*;

public interface ItemRepository {

    public void add(Product receiptItem);

    public List<ReceiptItem> findBy(String[] barcodes);

    public ReceiptItem barcodeToReceiptItem(String barcode);
}
