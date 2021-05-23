package mob.code.supermarket;

import mob.code.supermarket.bean.*;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductFactory {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductFactory(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(Product item) {
        Map<String, Object> args = new HashMap<>();
        args.put("barcode", item.barcode());
        args.put("name", item.name());
        args.put("unit", Optional.ofNullable(item.unit()).orElse(""));
        args.put("price", BigDecimal.valueOf(item.price()));
        args.put("type", Integer.valueOf(item.type()));
        namedParameterJdbcTemplate.update("insert into item (barcode,name,unit,price,type) values (:barcode,:name,:unit,:price,:type)", args);
//        System.out.println(jdbcTemplate.query("select * from item",new ColumnMapRowMapper()));
    }

    public void clear() {
        jdbcTemplate.execute("truncate table item");
    }
}
