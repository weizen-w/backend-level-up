package de.ait.shop.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.ait.shop.repositories.UsersRepository;
import de.ait.shop.repositories.impl.UsersRepositoryJdbcImpl;
import de.ait.shop.services.UsersService;
import de.ait.shop.services.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * 9/25/2023
 * Spring - Java Config
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public UsersService usersService(UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }

    @Bean
    public UsersRepository usersRepository(DataSource dataSource) {
        return new UsersRepositoryJdbcImpl(dataSource);
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
