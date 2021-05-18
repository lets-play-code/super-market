package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It is a demo of legacy data access code for exercise purpose only.
 * Don't use the code as a reference.
 */
@Component
public class ItemDao {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    public List<Item> getSampleItems() {
        try (Connection conn = DriverManager.getConnection(jdbcUrl,
                dbUser, dbPassword); Statement stmt = conn.createStatement()) {
            ArrayList<Item> items = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("select * from item limit 3");
            while (rs.next()) {
                String barcode = rs.getString("barcode");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                double price = rs.getDouble("price");
                int type = rs.getInt("type");
                items.add(new Item(barcode, name, unit, price, type == 0 ? "individual" : "weight"));
            }
            return items;
        } catch (SQLException e) {
            throw new SupermarketException(e);
        }
    }
}
