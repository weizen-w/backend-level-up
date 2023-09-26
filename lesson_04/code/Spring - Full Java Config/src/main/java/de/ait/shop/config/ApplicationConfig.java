package de.ait.shop.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 9/25/2023
 * Spring - Java Config
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "de.ait.shop")
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(environment.getProperty("hikari.jdbc.url"));
        config.setUsername(environment.getProperty("hikari.jdbc.username"));
        config.setPassword(environment.getProperty("hikari.jdbc.password"));
        config.setMaximumPoolSize(environment.getProperty("hikari.jdbc.maxPoolSize", Integer.class));

        return config;
    }
}
