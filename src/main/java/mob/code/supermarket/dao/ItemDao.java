package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import mob.code.supermarket.model.SupermarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public void saveSampleData() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            System.out.println("Inserting records into the table...");
            String sql = "INSERT INTO item VALUES (12345678, 'pizza', '', 15, 0)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO item VALUES (22345678, 'milk', 'L', 12.3, 1)";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");

        } catch (SQLException e) {
            throw new SupermarketException(e);
        }
    }

    public void clear() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "delete from item;";
            stmt.executeUpdate(sql);
            System.out.println("Deleted records from the table...");

        } catch (SQLException e) {
            throw new SupermarketException(e);
        }
    }
}
