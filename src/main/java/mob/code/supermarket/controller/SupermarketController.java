package mob.code.supermarket.controller;

import mob.code.supermarket.bean.BuyItem;
import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Receipt;
import mob.code.supermarket.bean.ReceiptItem;
import mob.code.supermarket.dao.ItemRepository;
import mob.code.supermarket.dto.Response;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class SupermarketController {
    private ItemRepository itemRepository;

    public SupermarketController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @PostMapping("scan")
    public Response<List<String>> scan(@RequestBody List<String> items) {
        List<BuyItem> buyItems = items.stream().map(BuyItem::from).collect(Collectors.toList());

        List<ReceiptItem> receiptItems = buyItems.stream().map(buyItem -> {
            Item item = itemRepository.findByBarcode(buyItem.getBarcode()).orElse(null);
            return new ReceiptItem(item.getName(), buyItem.getCount(), item.getPrice(), item.getUnit());
        }).collect(Collectors.toList());

        Receipt receipt = Receipt.of(receiptItems);
        return Response.of(receipt.output());
    }


}
