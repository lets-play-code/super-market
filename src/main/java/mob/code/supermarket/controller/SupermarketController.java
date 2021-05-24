package mob.code.supermarket.controller;

import mob.code.supermarket.bean.*;
import mob.code.supermarket.bean.ItemRepository;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.legacy.BarcodeReader;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class SupermarketController {
    private final ItemRepository itemRepository;

    public SupermarketController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @PostMapping("scan")
    public Response<List<String>> scan(@RequestBody List<String> items) {
        List<String> newItems = BarcodeReader.barcodeFactory().getBarcode(String.join("\n", items));
        ReceiptItemFactory receiptItemsFactory = new ReceiptItemFactory(itemRepository);
        List<ReceiptItem> receiptItems = new BuyItems(newItems).mapToList(receiptItemsFactory::create);
        Receipt receipt = Receipt.of(receiptItems);
        return Response.of(receipt.output());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public Response itemNotFound(ItemNotFoundException e) {
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(WrongQuantityException.class)
    public Response wrongQuantity(WrongQuantityException e) {
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(BarCodeException.class)
    public Response barcodeException(BarCodeException e) {
        return Response.error(e.getMessage());
    }
}
