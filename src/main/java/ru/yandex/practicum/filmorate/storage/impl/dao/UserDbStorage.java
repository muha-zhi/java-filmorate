package ru.yandex.practicum.filmorate.storage.impl.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users(name, login, email, birthday) " +
                "values (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday());

        return user;
    }

    @Override
    public boolean delUserById(long id) {
        String sql = "DELETE from users where user_id = ?";
        return (jdbcTemplate.update(sql, id)) > 0;

    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET name = ?, login = ?, email = ?, birthday = ? " +
                "WHERE user_id = ?";

        jdbcTemplate.update(sql,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        return user;
    }

    @Override
    public User getUserById(long id) {

        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE user_id = ?", id);
        if (userRows.next()) {
            User user = new User();
            user.setId(userRows.getInt("user_id"));
            user.setName(userRows.getString("name"));
            user.setLogin(userRows.getString("login"));
            user.setEmail(userRows.getString("email"));
            LocalDate birthday = Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate();
            if (birthday != null) {
                user.setBirthday(Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate());
            }

            log.info("Найден пользователь: {} {}", user.getId(), user.getName());

            return user;
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    public User makeUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    }
}
