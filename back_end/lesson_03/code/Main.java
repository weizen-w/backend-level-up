package de.ait.shop;

import java.sql.*;

/**
 * 9/11/2023
 * Spring
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main {
    public static void main(String[] args) {
        // jdbc:h2:file:~/databases/shop_db;AUTO_SERVER=TRUE
        try {
            Class.forName("org.h2.Driver"); // загружаем драйвер в виртуальную машину
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        try {
            // попробуем сделать подключение к базе
            // если базы еще не было - он должен ее создать
            Connection connection = DriverManager.getConnection("jdbc:h2:file:~/databases/shop_db;AUTO_SERVER=TRUE",
                    "admin", "qwerty007");

            Statement statement = connection.createStatement(); // создали объект для отправки запроса в базу данных
            ResultSet resultSet = statement.executeQuery("select * from account;"); // отправили запрос в базу данных
            // пробегаем по результату и печатаем его
            while (resultSet.next()) {
                System.out.println(resultSet.getString("email") + " " + resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
