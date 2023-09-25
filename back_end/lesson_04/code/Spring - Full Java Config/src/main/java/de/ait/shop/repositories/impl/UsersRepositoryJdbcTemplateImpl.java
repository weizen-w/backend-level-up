package de.ait.shop.repositories.impl;

import de.ait.shop.models.User;
import de.ait.shop.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 9/25/2023
 * Spring - Full Java Config
 *
 * @author Marsel Sidikov (AIT TR)
 */
@RequiredArgsConstructor
@Repository
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from account order by id";

    //language=SQL
    private static final String SQL_SELECT_ONE_BY_EMAIL = "select * from account where email = ? limit 1";

    private static final RowMapper<User> USER_ROW_MAPPER = (row, rowNum) -> User.builder()
            .id(row.getLong("id"))
            .email(row.getString("email"))
            .password(row.getString("password"))
            .build();

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, USER_ROW_MAPPER);
    }

    @Override
    public void save(User model) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("account")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> row = new HashMap<>();
        row.put("email", model.getEmail());
        row.put("password", model.getPassword());

        Long generatedId = insert.executeAndReturnKey(row).longValue();

        model.setId(generatedId);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public User findOneByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ONE_BY_EMAIL, USER_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
