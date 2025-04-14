package br.com.realtech.ancora.configs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Configuration
public class TestDatabaseInitializer {
    @Bean
    public ApplicationRunner initTestDatabase(DataSource dataSource) {
        return args -> {
            String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name       VARCHAR(100) NOT NULL,
                    email      VARCHAR(100) NOT NULL,
                    password   VARCHAR(100) NOT NULL,
                    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
                
                    CONSTRAINT unique_user_email UNIQUE (email)
                );
                """;

            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        };
    }
}
