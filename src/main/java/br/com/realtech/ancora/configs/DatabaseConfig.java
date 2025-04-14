package br.com.realtech.ancora.configs;

import br.com.realtech.ancora.factories.ConnectionFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:ancora_hub");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }

    @Bean
    public ConnectionFactory connectionFactory(DataSource dataSource) {
        return new ConnectionFactory(dataSource);
    }
}
