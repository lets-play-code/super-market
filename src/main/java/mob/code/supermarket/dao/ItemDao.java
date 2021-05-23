package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * It is a demo of legacy data access code for exercise purpose only.
 * Don't use the code as a reference.
 */
@Component
public class ItemDao {

    @Autowired
    private DataSource dataSource;

    public Item findByBarcode(String barcode) {
        return getSampleItems().stream()
                .filter(item -> Objects.equals(item.getBarcode(), barcode))
                .findFirst()
                .orElseThrow(() -> new SupermarketException(String.format("item doesn't exist: %s", barcode)));
    }

    public List<Item> getSampleItems() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
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

    public void save(Item item) {
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement("INSERT INTO item VALUES (?, ?, ?, ?, ?)")
        ) {
            preparedStatement.setString(1, item.getBarcode());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setString(3, item.getUnit());
            preparedStatement.setDouble(4, item.getPrice());
            preparedStatement.setString(5, item.getType());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SupermarketException(e);
        }
    }

    public void clear() {
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM item");
        } catch (SQLException e) {
            throw new SupermarketException(e);
        }
    }
}
