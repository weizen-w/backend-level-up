package de.ait.shop;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.ait.shop.models.User;
import de.ait.shop.repositories.impl.UsersRepositoryJdbcImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 9/25/2023
 * Spring
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main {
    public static void main(String[] args) {
        // создаем конфигурацию ConnectionPool от Hikari
        HikariConfig config = new HikariConfig();
        // указываем URL-подключения
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/shop_db");
        // настройки пользователя для подключения
        config.setUsername("postgres");
        config.setPassword("qwerty007");
        // размер пула (сколько потоков и сколько Connection-ов)
        config.setMaximumPoolSize(50);

        // создаем объект DataSource
        DataSource dataSource = new HikariDataSource(config);

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/shop_db");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("qwerty007");

        UsersRepositoryJdbcImpl usersRepository = new UsersRepositoryJdbcImpl(dataSource);

        try (ExecutorService service = Executors.newFixedThreadPool(10)) {
            for (int i = 0; i < 10; i++) {
                service.submit(() -> {
                    System.out.println("task submit");
                    for (int j = 0; j < 10_000; j++) {
                        usersRepository.save(User.builder().email("email").password("qwerty007").build());
                        System.out.println(Thread.currentThread().getName() + " inserted " + j);
                    }
                });
            }
        }


    }
}
