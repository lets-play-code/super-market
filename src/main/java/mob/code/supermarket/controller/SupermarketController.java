package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.dao.ItemDao;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.entity.Barcode;
import mob.code.supermarket.entity.Receipt;
import mob.code.supermarket.entity.ReceiptItem;
import mob.code.supermarket.legacy.BarcodeReader;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @PostMapping("scan")
    public Response<List<String>> scan(@RequestBody List<String> barcodes) {
        // 1. code 数量
        List<Barcode> barcodes1 = Barcode.readBarcodes(barcodes);

        // 2. code -> 商品
        // 3. 商品 -> 收据条目
        List<ReceiptItem> receiptItemList = barcodes1.stream().map(x -> {
            Item item = itemDao.getItem(x.getCode());
            if (Objects.isNull(item)) {
                throw new SupermarketException("item doesn't exist: " + x.getCode());
            }
            return new ReceiptItem(item.getName(), x.getNumber(), BigDecimal.valueOf(item.getPrice()), item.getUnit());
        }).collect(Collectors.toList());


        // 4. 格式化输出
        Receipt receipt = new Receipt();
        receipt.addAll(receiptItemList);
        return Response.of(receipt.toResult());
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
