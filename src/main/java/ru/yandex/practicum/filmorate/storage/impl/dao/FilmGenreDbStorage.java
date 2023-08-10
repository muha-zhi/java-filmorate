package ru.yandex.practicum.filmorate.storage.impl.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ru.yandex.practicum.filmorate.storage.impl.dao.GenreDbStorage.GENRE_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public static final String FILM_GENRE_FILM_ID = "film_id";
    public static final String FILM_GENRE_GENRE_ID = "genre_id";

    public static final String FILM_GENRE_TABLE = "film_genre";


    @Override
    public void addFG(long idOfFilm, long idOfGenre) {
        if (!genreContOfFilm(idOfFilm, idOfGenre)) {
            String sql = "INSERT INTO " + FILM_GENRE_TABLE + " (" + FILM_GENRE_FILM_ID + ", " + FILM_GENRE_GENRE_ID + ")" +
                    " VALUES(?, ?)";

            jdbcTemplate.update(sql, idOfFilm, idOfGenre);
        }
    }

    @Override
    public void delFG(long idOfFilm, long idOfGenre) {
        String sql = "DELETE FROM " + FILM_GENRE_TABLE + " WHERE " + FILM_GENRE_FILM_ID + "  = ? AND " + FILM_GENRE_GENRE_ID + " = ?";
        jdbcTemplate.update(sql, idOfFilm, idOfGenre);
    }

    @Override
    public List<Genre> getAllGenForFilm(long filmId) {
        String sql = "SELECT * FROM " + FILM_GENRE_TABLE + " AS fg INNER JOIN genre ON fg."
                + FILM_GENRE_GENRE_ID + " = genre." + FILM_GENRE_GENRE_ID +
                " WHERE " + FILM_GENRE_FILM_ID + " = ?";

        return jdbcTemplate.query(sql, new Object[]{filmId}, (rs, rowNum) -> makeGenre(rs));
    }


    @Override
    public void updateGenresOfFilm(long filmId, List<Genre> genres) {

        String sql = "DELETE FROM " + FILM_GENRE_TABLE + " WHERE " + FILM_GENRE_FILM_ID + " = ?";

        jdbcTemplate.update(sql, filmId);
        if (genres != null) {
            for (Genre g : genres) {

                addFG(filmId, g.getId());
            }
        }
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre(rs.getInt("genre." + GENRE_ID), rs.getString("name"));
        return genre;
    }

    private boolean genreContOfFilm(long filmId, long genreId) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM " + FILM_GENRE_TABLE + " WHERE "
                + FILM_GENRE_FILM_ID + " = ? AND " + FILM_GENRE_GENRE_ID + " = ?", filmId, genreId);
        if (filmRows.next()) {
            return true;
        } else {
            return false;
        }
    }

}
