package mob.code.supermarket.bean;

import java.util.Arrays;
import java.util.List;

/**
 * @author Simon
 * @date 2021/5/22 14:26
 */
public class Receipt {
    public List<String> print() {
        List<String> expectedContent = Arrays.asList(
                "****** SuperMarket receipt ******",
                "pizza: 1 x 15.00 --- 15.00",
                "milk: 3(L) x 12.30 --- 36.90",
                "---------------------------------",
                "total: 51.90(CNY)",
                "*********************************"
        );

        return expectedContent;
    }
}
