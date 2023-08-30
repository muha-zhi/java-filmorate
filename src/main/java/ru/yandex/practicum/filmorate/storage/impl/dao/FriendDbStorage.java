package ru.yandex.practicum.filmorate.storage.impl.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<User> getAllFriendOfUser(long userId) {

        String sql = "SELECT * FROM adding_friends AS ad" +
                " INNER JOIN users AS us ON ad.to_user_id = us.user_id WHERE from_user_id = " + userId;


        List<User> us = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));

        return us;
    }

    @Override
    public void removeFriendById(long friendId, long userId) {
        String sql = "DELETE FROM adding_friends WHERE from_user_id = ? AND to_user_id = ?";

        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void addFriend(long friendId, long userId) {
        String sql = "INSERT INTO adding_friends(from_user_id, to_user_id) VALUES(?, ?)";

        jdbcTemplate.update(sql, userId, friendId);
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
