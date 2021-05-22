package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dao.ItemDao;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.legacy.BarcodeReader;
import mob.code.supermarket.model.Receipt;
import mob.code.supermarket.model.ReceiptItem;
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

        /*
        1. code -> goods + quantity
        2. goods + quantity -> receipt items
        3. receipt formatting
         */

        Receipt receipt = new Receipt();
        for (String scan : barcodes) {
            String[] split = scan.split("-");
            String barcode = split[0];
            int quantity = 1;
            if (split.length > 1) {
                quantity = Integer.parseInt(split[1]);
            }
            Item item = itemDao.findByBarcode(barcode);
            receipt.add(new ReceiptItem(item, quantity));
        }
        String[] format = receipt.format();
        return Response.of(format);
    }
}
