package mob.code.supermarket;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class SupermarketControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_ping() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());
    }

    protected void dbsetUp(String datafile) {
        IDatabaseConnection connection = null;
        try {
//获得DB连接
            connection = new DatabaseConnection(dataSource.getConnection());

////对数据库中的操作对象表student进行备份
//            QueryDataSet backupDataSet = new QueryDataSet(connection);
//            backupDataSet.addTable("student");
//            File file= File.createTempFile("student_back",".xml");//备份文件
//            FlatXmlDataSet.write(backupDataSet,new FileOutputStream(file));

//准备数据的读入
            IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(datafile));
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
            }
        }
    }


    @Test
    @Sql(scripts = "start.sql")
    public void test_scan() throws Exception {
        dbsetUp("/Users/georgewu/workbench/kata/super-market/src/test/java/mob/code/supermarket/test.xml");

        String requestJson = "[\n" +
                "\"12345678\",\n" +
                "\"22345678-3\"\n" +
                "]";

        String expectedResponseJson = "{\"data\":[\"****** SuperMarket receipt ******\",\"pizza: 1 x 15.00 --- 15.00\",\"milk: 3(L) x 12.30 --- 36.90\",\"---------------------------------\",\"total: 51.90(CNY)\",\"*********************************\"],\"error\":null}";

        String actualResponseJson = mockMvc.perform(post("/scan").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(actualResponseJson).isEqualTo(expectedResponseJson);
//        JSONAssert.assertEquals(expectedResponseJson, actualResponseJson, false);
    }
}
