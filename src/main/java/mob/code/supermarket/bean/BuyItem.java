package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuyItem {

    private String barcode;

    private Integer count;

    public static BuyItem from(String str) {
        String[] split = str.split("-");
        if (split.length == 2) {
            return new BuyItem(split[0], Integer.parseInt(split[1]));
        }
        return new BuyItem(str, 1);
    }
}
