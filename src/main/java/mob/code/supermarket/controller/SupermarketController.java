package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.legacy.BarcodeReader;
import mob.code.supermarket.model.Receipt;
import mob.code.supermarket.model.ReceiptItem;
import mob.code.supermarket.model.SupermarketException;
import mob.code.supermarket.repository.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class SupermarketController {
    private final ItemRepository itemRepository;

    public SupermarketController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("ping")
    public Response<String> ping() {
        return Response.of("pong");
    }

    @GetMapping("dontcall")
    public Response<String> responseError() {
        throw new SupermarketException("It is a sample error");
    }

    @GetMapping("item")
    public Response<List<Item>> getItems() {
        return Response.of(itemRepository.findAll());
    }

    @PostMapping("tryBarCode")
    public List<String> tryBarCode(@RequestBody String[] barcodes) {
        try {
            String toOcr = String.join("\n", Arrays.asList(barcodes));
            BarcodeReader barcodeReader = BarcodeReader.barcodeFactory();
            barcodeReader.getBarcode(toOcr);
            return barcodeReader.barcodes;
        } catch (Exception e) {
            throw new SupermarketException("can not recognize barcode:\n" +
                    String.join("\n", Arrays.asList(barcodes)));
        }
    }

    @PostMapping("scan")
    public Response<String[]> scan(@RequestBody String[] barcodes) {
        List<ReceiptItem> receiptItems = Arrays.stream(barcodes)
                .map(this::getReceiptItem)
                .collect(Collectors.toList());
        Receipt receipt = new Receipt(receiptItems);
        return Response.of(receipt.format());
    }

    private ReceiptItem getReceiptItem(String scan) {
        BarcodeParser barcodeParser = new BarcodeParser(scan);
        String barcode = barcodeParser.getBarCode();
        Item item = itemRepository.findByBarcode(barcode)
                .orElseThrow(() -> new SupermarketException(String.format("item doesn't exist: %s", barcode)));
        return new ReceiptItem(item, barcodeParser.getQuantity());
    }
}
