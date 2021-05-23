package mob.code.supermarket.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: BarcodeAndCount <br>
 * date: 2021/5/22 下午2:50 <br>
 * author: Feng_001 <br>
 * version: 1.0 <br>
 */
@Data
@NoArgsConstructor
public class BarcodeAndCount {
    private  String barcode;
    private  int count;
    private String inbarcode;

    public BarcodeAndCount(String barcode, int count, String inbarcode) {
        this.barcode = barcode;
        this.count = count;
        this.inbarcode = inbarcode;
    }
}

