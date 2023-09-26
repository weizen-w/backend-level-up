package de.ait.shop;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 9/18/2023
 * Spring
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main3 {
    public static void main(String[] args) {

        // создаем конфигурацию ConnectionPool от Hikari
        HikariConfig config = new HikariConfig();
        // указываем URL-подключения
        config.setJdbcUrl("jdbc:h2:file:~/databases/shop_db;AUTO_SERVER=TRUE");
        // настройки пользователя для подключения
        config.setUsername("admin");
        config.setPassword("qwerty007");
        // размер пула (сколько потоков и сколько Connection-ов)
        config.setMaximumPoolSize(50);

        // создаем объект DataSource
        DataSource dataSource = new HikariDataSource(config);


        long before = System.currentTimeMillis();
        // внутри каждого потока начинаем делать 100 000 записей в одну базу
        // 10 потоков, каждый поток будет писать 100 000 записей в базу
        try (ExecutorService service = Executors.newFixedThreadPool(10)) {
            for (int j = 0; j < 10; j++) {
                service.submit(() -> {
                    for (int i = 0; i <= 10_000; i++) {
                        try {
                            // забираем подключение у HikariCP
                            Connection connection = dataSource.getConnection();

                            Statement statement = connection.createStatement(); // создали объект для отправки запроса в базу данных

                            int affectedRow = statement.executeUpdate("insert into account(first_name, last_name, email, password) " +
                                    "values ('Marsel', 'Sidikov', 'temp@gmail.com', 'qwerty008')");
                            System.out.println(connection + "  " + Thread.currentThread().getName() + ": Строк добавлено " + affectedRow);
                            // закрываем все открытые объекты
                            // иначе у Hikari будет утечка памяти
                            statement.close();
                            connection.close(); // означает, что нужно освободить подключение
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }
}
