package ru.yandex.practicum.filmorate.storage.impl.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film getFilmById(long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE film_id = ?", id);
        if (filmRows.next()) {
            Film film = new Film();
            film.setId(filmRows.getInt("film_id"));
            film.setName(filmRows.getString("name"));
            film.setDescription(filmRows.getString("description"));
            film.setReleaseDate(Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate());
            film.setDuration(filmRows.getInt("duration"));
            film.setGenre(filmRows.getInt("genre_id"));
            log.info("Найден Фильм : {} {}", film.getId(), film.getName());

            return film;
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            return null;
        }
    }

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO films(name, description, release_date, duration, genre_id) " +
                "values (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenre()
                );

        return film;
    }

    @Override
    public void delFilmById(long id) {
        String sql = "DELETE from films where film_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Film updateFilm(Film film) {
         String sql = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, genre_id = ?" +
                 " WHERE film_id = ?";


        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenre(),
                film.getId()

        );
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT * FROM films";
       return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));

    }

    public Film makeFilm(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("film_id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        film.setGenre(rs.getInt("genre_id"));
        return film;
    }
}
