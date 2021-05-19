package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dao.ItemDao;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.dto.ScanInput;
import mob.code.supermarket.model.OrderItem;
import mob.code.supermarket.model.Receipt;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SupermarketController {
    public static final String ITEM_DOES_NOT_EXIST_MESSAGE = "item doesn't exist: ";
    @Autowired
    ItemDao itemDao;

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
        return Response.of(itemDao.getSampleItems());
    }

    @PostMapping("scan")
    public Response<List<String>> scan(@RequestBody List<ScanInput> inputs) {
        Receipt receipt = new Receipt();
        for (ScanInput input : inputs) {
            Item item = itemDao.findItem(input.getBarcode())
                    .orElseThrow(() -> itemNotExistException(input.getBarcode()));
            OrderItem orderItem = new OrderItem(item, input);
            receipt.addItem(orderItem);
        }
        return Response.of(receipt.output());
    }

    private SupermarketException itemNotExistException(String barcode) {
        return new SupermarketException(ITEM_DOES_NOT_EXIST_MESSAGE + barcode);
    }
}
