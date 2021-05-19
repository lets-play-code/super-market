package mob.code.supermarket.dao;

import mob.code.supermarket.bean.Item;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemDaoTest {
    private ItemDao itemDao;
    private PostgreSQLContainer<?> postSql;

    @Before
    public void setUp() throws SQLException {
        postSql = new PostgreSQLContainer<>("postgres:9.6-alpine")
                .withUsername("postgres").withPassword("abc");
        postSql.start();
        Connection connection = postSql.createConnection("");
        connection.createStatement().execute("CREATE TABLE item (" +
                "                barcode text NOT NULL primary key," +
                "                name text NOT NULL," +
                "                unit text NOT NULL," +
                "                price money NOT NULL," +
                "                type int NOT NULL" +
                ");");

        itemDao = new ItemDao(postSql.getJdbcUrl(), "postgres", "abc");
    }

    @Test
    public void testFindItemByBarcode() throws SQLException {
        Connection connection = postSql.createConnection("");
        connection.createStatement().execute("insert into item(barcode, name, unit, price, type) VALUES" +
                "('22345', 'cake', '', 5, 0)," +
                "('32345', 'rice', 'KG', 1.7, 1);");
        assertThat(itemDao.findItem("12345")).isEmpty();
        assertThat(itemDao.findItem("22345")).hasValue(new Item("22345", "cake", "", 5, "individual"));
        assertThat(itemDao.findItem("32345")).hasValue(new Item("32345", "rice", "KG", 1.7, "weight"));
    }
}