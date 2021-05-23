package mob.code.supermarket.controller;

import mob.code.supermarket.bean.*;
import mob.code.supermarket.bean.ItemRepository;
import mob.code.supermarket.dto.Response;
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
        List<ReceiptItem> receiptItems = new BuyItems(new BuyItems(items).toNewList(items))
                .toList()
                .map(buyItem -> {
                    Item item = itemRepository.findByBarcode(buyItem.getBarcode()).orElseThrow(() -> new ItemNotFoundException(buyItem.getBarcode()));
                    return new ReceiptItem(item.getName(), item.getPrice(), item.getUnit(), item.getType(),buyItem);
                }).collect(Collectors.toList());
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
}
