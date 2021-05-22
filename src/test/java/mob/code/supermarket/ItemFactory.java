package mob.code.supermarket;

import mob.code.supermarket.bean.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ItemFactory {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemFactory(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(Item item) {
        Map<String, Object> args = new HashMap<>();
        args.put("barcode", item.getBarcode());
        args.put("name", item.getName());
        args.put("unit", Optional.ofNullable(item.getUnit()).orElse(""));
        args.put("price", BigDecimal.valueOf(item.getPrice()));
        args.put("type", 0);
        namedParameterJdbcTemplate.update("insert into item (barcode,name,unit,price,type) values (:barcode,:name,:unit,:price,:type)", args);
    }

    public void clear() {
        jdbcTemplate.execute("truncate table item");
    }
}
