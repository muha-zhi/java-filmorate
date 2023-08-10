package ru.yandex.practicum.filmorate.storage.impl.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate template;

    public static final String MPA_TABLE = "mpa";
    public static final String MPA_ID = "mpa_id";
    public static final String MPA_NAME = "name";

    @Override
    public void addMpa(Mpa rating) {
        String sql = "INSERT INTO " + MPA_TABLE + "(" + MPA_NAME + ") VALUES(?)";

        template.update(sql, rating.getName());
    }

    @Override
    public Mpa getMpaById(long id) {
        SqlRowSet ratingRows = template.queryForRowSet("SELECT " + MPA_ID + ", " + MPA_NAME + " FROM " + MPA_TABLE + " WHERE " + MPA_ID + " = ?", id);
        if (ratingRows.next()) {
            Mpa rating = new Mpa(ratingRows.getInt(MPA_ID), ratingRows.getString(MPA_NAME));

            log.info("Найден рейтинг : {} {}", rating.getId(), rating.getName());

            return rating;
        } else {
            log.info("Рейтинг с идентификатором {} не найден.", id);
            return null;
        }
    }


    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM mpa ";
        return template.query(sql, (rs, rowNum) -> makeMpa(rs));
    }


    public Mpa makeMpa(ResultSet rs) throws SQLException {

        return new Mpa(rs.getInt("mpa_id"), rs.getString("name"));
    }


    @Override
    public void delMpaById(long id) {
        String sql = "DELETE FROM mpa WHERE mpa_id = ?";
        template.update(sql, id);
    }
}
