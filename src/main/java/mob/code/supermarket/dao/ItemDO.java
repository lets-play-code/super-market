package mob.code.supermarket.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mob.code.supermarket.bean.Item;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "item")
public class ItemDO {
    @Id
    private String barcode;
    private String name;
    private String unit;
    private BigDecimal price;
    private String type;

    public Item toEntity() {
        return new Item(barcode, name, unit, price, type);
    }

    public static ItemDO fromEntity(Item entity) {
        return ItemDO.builder()
                .price(entity.getPriceValue())
                .name(entity.getName())
                .type(entity.getType())
                .unit(entity.getUnit())
                .barcode(entity.getBarcode())
                .build();
    }
}
