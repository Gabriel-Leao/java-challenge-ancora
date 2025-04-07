package br.com.realtech.ancora.configs;

import br.com.realtech.ancora.factories.ConnectionFactory;
import br.com.realtech.ancora.utils.EnvUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    private final String dbHost = EnvUtil.getEnv("DB_HOST");
    private final String dbName = EnvUtil.getEnv("DB_NAME");
    private final String dbUser = EnvUtil.getEnv("DB_USER");
    private final String dbPassword = EnvUtil.getEnv("DB_PASSWORD");

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://" + dbHost + "/" + dbName);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }

    @Bean
    public ConnectionFactory connectionFactory(DataSource dataSource) {
        return new ConnectionFactory(dataSource);
    }
}
