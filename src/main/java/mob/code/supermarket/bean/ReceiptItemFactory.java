package mob.code.supermarket.bean;

import java.util.function.Function;

public class ReceiptItemFactory {
    private final Function<BuyItem, Item> itemFetcher;

    public ReceiptItemFactory(ItemRepository itemRepository) {
        itemFetcher = (buyItem) -> itemRepository.findByBarcode(buyItem.getBarcode()).orElseThrow(() -> new ItemNotFoundException(buyItem.getBarcode()));
    }

    public ReceiptItem create(BuyItem buyItem) {
        ReceiptItem receiptItem = new ReceiptItem(buyItem, itemFetcher);
        receiptItem.checkQuantity();
        return receiptItem;
    }

}
