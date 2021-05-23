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
        List<QuantifiedBarcode> barcodeList = getQuantifiedBarcodes(barcodes);
        List<ReceiptItem> receiptItems = getReceiptItems(barcodeList);
        Receipt receipt = new Receipt(receiptItems);
        return receipt.format();
    }

    private List<QuantifiedBarcode> getQuantifiedBarcodes(String[] barcodes) {
        BarcodeParser barcodeParser = new BarcodeParser(barcodes);
        return barcodeParser.getBarcodes();
    }

    private List<ReceiptItem> getReceiptItems(List<QuantifiedBarcode> barcodeList) {
        return barcodeList.stream()
                .map(this::getReceiptItem)
                .collect(Collectors.toList());
    }

    private ReceiptItem getReceiptItem(QuantifiedBarcode barcodeItem) {
        String barcode = barcodeItem.getBarCode();
        return new ReceiptItem(getItem(barcode), barcodeItem.getQuantity());
    }

    private Item getItem(String barcode) {
        return itemRepository.findByBarcode(barcode)
                .orElseThrow(() -> new SupermarketException(String.format("item doesn't exist: %s", barcode)));
    }

}
