package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Item;
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
}
