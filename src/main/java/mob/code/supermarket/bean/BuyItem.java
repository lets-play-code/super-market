package mob.code.supermarket.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BuyItem {

    private String barcode;

    private double count;

    public static BuyItem from(String str) {
        String[] split = str.split("-");
        if (split.length == 2) {
            return new BuyItem(split[0], Double.parseDouble(split[1]));
        }
        return new BuyItem(str, 1);
    }
}
