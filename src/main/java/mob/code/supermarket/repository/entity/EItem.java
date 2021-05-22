package mob.code.supermarket.repository.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Builder
@Entity
@Table(name = "item")
public class EItem {
    @Id
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private String type;
    @Tolerate
    public EItem() {}

}
