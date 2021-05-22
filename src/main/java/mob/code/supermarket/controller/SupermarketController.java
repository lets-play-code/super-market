package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Receipt;
import mob.code.supermarket.bean.ReceiptItem;
import mob.code.supermarket.dao.ItemDao;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.legacy.BarcodeReader;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class SupermarketController {
    @Autowired
    ItemDao itemDao;


    @PostMapping("scan")
    public Response<List<String>> scan(@RequestBody List<String> items) {
        Receipt receipt = Receipt.of(Arrays.asList(
                new ReceiptItem("pizza", 1, 15.00, null),
                new ReceiptItem("milk", 3, 12.30, "L")
        ));
        return Response.of(receipt.output());
    }


}
