package mob.code.supermarket.controller;

import mob.code.supermarket.bean.*;
import mob.code.supermarket.dao.ItemDAO;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.legacy.BarcodeReader;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/")
public class SupermarketController {
    @Autowired
    ItemDAO itemDao;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("ping")
    public Response<String> ping() {
        return Response.of("pong");
    }

    @GetMapping("dontcall")
    public Response<String> responseError() {
        throw new SupermarketException("It is a sample error");
    }

//    @GetMapping("item")
//    public Response<List<Item>> getItems() {
//        return Response.of(itemDao.getSampleItems());
//    }

    @PostMapping("scan")
    public Response<List<String>> scan(@RequestBody String[] barcodes) {
//        //分解
//        //1.code 解析
//        //2.找到商品
//        //3.商品->票据的转换结构
//        //4.打印
//        productRepository.add(new Product("12345678","pizza", "", 15.00));
//        productRepository.add(new Product("22345678","milk", "L", 12.30));
        //barcode 拿到商品 拿到数量
        //story2
        //在这里把输入转换为第一步的格式 barcode-n的形式


        Map<String, Integer> barcodeCount = new HashMap<>();
        for (String input : barcodes) {
            barcodeCount.put(input, barcodeCount.getOrDefault(input, 0) + 1);
        }
//        String[] barcodeWithCount =
        for (Map.Entry<String, Integer> entry : barcodeCount.entrySet()) {

        }
        Receipt receipt = new Receipt();


        for (String input : barcodes) {
            Order order = new Order(input);
            String barcode = order.barcode();
            int count = order.count();
            Product product = productRepository.findByBarcode(barcode);
            receipt.add(new ReceiptItem(product.name(), count, product.unit(), product.price()));
        }
        return Response.of(receipt.print());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public Response itemNotFound(ItemNotFoundException e) {
        return Response.error(e.getMessage());
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
