package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * It is a demo of legacy data access code for exercise purpose only.
 * Don't use the code as a reference.
 */
@Component
public class ItemDao {

    public static final String SELECT_ITEM_BY_CODE = "select * from item where barcode=?";

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    public ItemDao() {
    }

    ItemDao(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public List<Item> getSampleItems() {
        try (Connection conn = DriverManager.getConnection(jdbcUrl,
                dbUser, dbPassword); Statement stmt = conn.createStatement()) {
            ArrayList<Item> items = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("select * from item limit 3");
            while (rs.next()) {
                items.add(toItem(rs));
            }
            return items;
        } catch (SQLException e) {
            throw new SupermarketException(e);
        }
    }

    public Optional<Item> findItem(String barcode) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl,
                dbUser, dbPassword);  PreparedStatement stmt = conn.prepareStatement(SELECT_ITEM_BY_CODE)) {
            stmt.setString(1, barcode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return Optional.of(toItem(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new SupermarketException(e);
        }
    }

    private Item toItem(ResultSet rs) throws SQLException {
        String barcode = rs.getString("barcode");
        String name = rs.getString("name");
        String unit = rs.getString("unit");
        double price = rs.getDouble("price");
        int type = rs.getInt("type");
        Item item = new Item(barcode, name, unit, price, type == 0 ? "individual" : "weight");
        return item;
    }
}
