package mob.code.supermarket.bean;

import java.util.*;

public class DefaultItemRepository implements ProductRepository {
    private Map<String, Product> receiptItems = new HashMap<>();

    public void add(Product receiptItem) {
        this.receiptItems.put(receiptItem.barcode(), receiptItem);
    }

    public List<ReceiptItem> findBy(String[] barcodes) {
        List<ReceiptItem> result = new ArrayList<>();
        Arrays.stream(barcodes).forEach(barcode->{
            result.add(barcodeToReceiptItem(barcode));
        });
        return result;
    }

    public ReceiptItem barcodeToReceiptItem(String barcode){
        String[] split = barcode.split("-");
        Product product = receiptItems.get(split[0]);
        if(split.length==1){
            return new ReceiptItem(product.name(),1,product.unit(),product.price());
        }
        return new ReceiptItem(product.name(),Integer.parseInt(split[1]),product.unit(),product.price());
    }
}
