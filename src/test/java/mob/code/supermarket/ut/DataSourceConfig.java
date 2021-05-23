package mob.code.supermarket.ut;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {

    //@Bean
    public DataSource dataSource() {
        DockerImageName dockerImageName = DockerImageName.parse("postgres:9.6");
        PostgreSQLContainer sqlContainer = new PostgreSQLContainer(dockerImageName);
        sqlContainer.start();
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(sqlContainer.getDriverClassName())
                .username(sqlContainer.getUsername())
                .password(sqlContainer.getPassword())
                .url(sqlContainer.getJdbcUrl() )
                .build();
        return dataSource;
    }

}
