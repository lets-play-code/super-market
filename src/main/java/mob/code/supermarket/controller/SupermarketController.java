package mob.code.supermarket.controller;

import mob.code.supermarket.bean.Receipt;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class SupermarketController {
    private final ItemService itemService;

    public SupermarketController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("scan")
    public Response<List<String>> scan(@RequestBody String[] barcodes) {
        return Response.of(
                new Receipt(
                        itemService.makeOrders(Arrays.asList(barcodes))
                ).print()
        );
    }
}
