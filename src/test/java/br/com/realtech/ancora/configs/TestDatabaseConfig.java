package br.com.realtech.ancora.configs;

import br.com.realtech.ancora.factories.ConnectionFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import javax.sql.DataSource;

@Configuration
@Import(TestDatabaseInitializer.class)
public class TestDatabaseConfig {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setMaximumPoolSize(5);
        return dataSource;
    }

    @Bean
    public ConnectionFactory connectionFactory(DataSource dataSource) {
        return new ConnectionFactory(dataSource);
    }
}
