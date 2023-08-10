package ru.yandex.practicum.filmorate.storage.impl.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public static final String GENRE_TABLE = "genre";
    public static final String GENRE_ID = "genre_id";
    public static final String GENRE_NAME = "name";

    @Override
    public void addGenre(Genre genre) {
        String sql = "INSERT INTO ?(?) VALUES(?)";
        jdbcTemplate.update(sql, GENRE_TABLE, GENRE_NAME, genre.getName());
    }

    @Override
    public Genre getGenreById(long id) {
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT * FROM " + GENRE_TABLE + " WHERE " + GENRE_ID + " = ?", id);
        if (genreRows.next()) {
            Genre genre = new Genre(genreRows.getInt(GENRE_ID), genreRows.getString(GENRE_NAME));
            log.info("Найден жанр: {} {}", genre.getId(), genre.getName());

            return genre;
        } else {
            log.info("Жанр с идентификатором {} не найден.", id);
            return null;
        }

    }

    @Override
    public List<Genre> getAllGenre() {
        String sql = "SELECT * FROM " + GENRE_TABLE;

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public void delGenreById(long id) {
        String sql = "DELETE FROM ? WHERE ? = ?";

        jdbcTemplate.update(sql, GENRE_TABLE, GENRE_ID, id);
    }

    public Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt(GENRE_ID), rs.getString(GENRE_NAME));
    }
}
