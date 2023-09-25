package de.ait.shop.repositories.impl;

import de.ait.shop.models.User;
import de.ait.shop.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 9/11/2023
 * Spring
 *
 * @author Marsel Sidikov (AIT TR)
 */
@RequiredArgsConstructor
@Repository
public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from account order by id";

    //language=SQL
    private static final String SQL_SELECT_ONE_BY_EMAIL = "select * from account where email = ? limit 1";

    //language=SQL
    private static final String SQL_INSERT_ACCOUNT = "insert into account (email, password) values (?, ?);";

    private final DataSource dataSource;

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {
                while (resultSet.next()) {
                    User user = User.builder()
                            .id(resultSet.getLong("id"))
                            .email(resultSet.getString("email"))
                            .password(resultSet.getString("password"))
                            .build();

                    users.add(user);
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    @Override
    public void save(User model) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ACCOUNT)) {

            statement.setString(1, model.getEmail());
            statement.setString(2, model.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Cannot insert user");
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public User findOneByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ONE_BY_EMAIL)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return User.builder()
                            .id(resultSet.getLong("id"))
                            .email(resultSet.getString("email"))
                            .password(resultSet.getString("password"))
                            .build();

                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return null;
    }
}
