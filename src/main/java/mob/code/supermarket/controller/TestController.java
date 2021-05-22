package mob.code.supermarket.controller;

import com.alibaba.fastjson.JSONObject;
import mob.code.supermarket.bean.Item;
import mob.code.supermarket.bean.Order;
import mob.code.supermarket.bean.Receipt;
import mob.code.supermarket.dao.ItemDao;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.legacy.BarcodeReader;
import mob.code.supermarket.model.SupermarketException;
import mob.code.supermarket.repository.dao.ItemRepository;
import mob.code.supermarket.repository.entity.EItem;
import mob.code.supermarket.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    ItemRepository itemRepository;
    @GetMapping("item")
    public Response<String> ping() {
        List<EItem> all = itemRepository.findAll();
        return Response.of(JSONObject.toJSONString(all));
    }


}
