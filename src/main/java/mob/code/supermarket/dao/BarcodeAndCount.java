package mob.code.supermarket.dao;

import lombok.Data;

/**
 * description: BarcodeAndCount <br>
 * date: 2021/5/22 下午2:50 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@Data
public class BarcodeAndCount {
    private  String barcode;
    private  int count;

    public BarcodeAndCount(String barcode, int i) {
        this.barcode = barcode;
        this.count = i;
    }
}

