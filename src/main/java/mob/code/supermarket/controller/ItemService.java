package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.model.Receipt;
import mob.code.supermarket.model.ReceiptItem;
import mob.code.supermarket.model.SupermarketException;
import mob.code.supermarket.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    String[] getFormattedReceipt(String[] barcodes) {
        BarcodeParser barcodeParser = new BarcodeParser(barcodes);
        List<QuantifiedBarcode> barcodeList = barcodeParser.getBarcodes();
        List<ReceiptItem> receiptItems = barcodeList.stream()
                .map(barcodeItem -> {
                    String barcode = barcodeItem.getBarCode();
                    Item item = itemRepository.findByBarcode(barcode)
                            .orElseThrow(() -> new SupermarketException(String.format("item doesn't exist: %s", barcode)));
                    return new ReceiptItem(item, barcodeItem.getQuantity());
                })
                .collect(Collectors.toList());
        Receipt receipt = new Receipt(receiptItems);
        return receipt.format();
    }

}
