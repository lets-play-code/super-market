package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyItem {

    private String barcode;

    private Integer count;
}
