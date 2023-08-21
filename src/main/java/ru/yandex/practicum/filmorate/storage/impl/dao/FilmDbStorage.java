package ru.yandex.practicum.filmorate.storage.impl.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public static final String FILMS_TABLE = "films";

    public static final String FILM_ID = "film_id";
    public static final String FILM_NAME = "name";
    public static final String FILM_RELEASE_DATE = "release_date";
    public static final String FILM_DURATION = "duration";
    public static final String FILM_DESCRIPTION = "description";
    public static final String FILM_MPA_ID = "mpa_id";
    public static final String FILM_RATE = "rate";


    @Qualifier("filmGenreDbStorage")
    private final FilmGenreDbStorage filmGenreDbStorage;

    @Qualifier("mpaDbStorage")
    private final MpaDbStorage mpaDbStorage;

    @Override
    public Film getFilmById(long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM " + FILMS_TABLE + " WHERE " + FILM_ID + " = ?", id);
        if (filmRows.next()) {

            List<Genre> genres = filmGenreDbStorage.getAllGenForFilm(filmRows.getInt(FILM_ID));

            Mpa mpa = mpaDbStorage.getMpaById(filmRows.getInt(FILM_MPA_ID));

            Film film = new Film();
            film.setId(filmRows.getInt(FILM_ID));
            film.setName(filmRows.getString(FILM_NAME));
            film.setDescription(filmRows.getString(FILM_DESCRIPTION));
            film.setReleaseDate(Objects.requireNonNull(filmRows.getDate(FILM_RELEASE_DATE)).toLocalDate());
            film.setDuration(filmRows.getInt(FILM_DURATION));
            film.setGenres(genres);
            film.setRate(filmRows.getInt(FILM_RATE));
            film.setMpa(mpa);

            log.info("Найден Фильм : {} {}", film.getId(), film.getName());

            return film;
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            return null;
        }
    }

    public Film addFilm(Film film) {
        String sql = "INSERT INTO " + FILMS_TABLE + " (" + FILM_NAME + " , "
                + FILM_DESCRIPTION + " , "
                + FILM_RELEASE_DATE + " , "
                + FILM_DURATION + " , "
                + FILM_MPA_ID + " , "
                + FILM_RATE + " ) " +
                "values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            if (film.getMpa() == null) {
                ps.setNull(5, Types.BIGINT);
            } else {
                ps.setLong(5, film.getMpa().getId());
            }
            ps.setLong(6, film.getRate());
            return ps;
        }, keyHolder);
        int id = 0;
        if (keyHolder.getKey() != null) {
            id = keyHolder.getKey().intValue();
        }
        film.setId(id);

        List<Genre> genres = film.getGenres();
        if (genres != null) {
            for (Genre g : genres) {
                filmGenreDbStorage.addFilmGenre(id, g.getId());
            }
        }

        return getFilmById(id);
    }

    @Override
    public void delFilmById(long id) {
        String sql = "DELETE from " + FILMS_TABLE + " where " + FILM_ID + " = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE " + FILMS_TABLE + " SET " + FILM_NAME + " = ?, " + FILM_DESCRIPTION + " = ?, " + FILM_RELEASE_DATE + " = ?, " + FILM_DURATION + " = ?, " + FILM_RATE + " = ?, " + FILM_MPA_ID + " = ?" +
                " WHERE " + FILM_ID + " = ?";


        filmGenreDbStorage.updateGenresOfFilm(film.getId(), film.getGenres());

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId()

        );
        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT * FROM " + FILMS_TABLE;
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    public Film makeFilm(ResultSet rs) throws SQLException {

        List<Genre> genres = filmGenreDbStorage.getAllGenForFilm(rs.getInt(FILM_ID));
        Mpa mpa = mpaDbStorage.getMpaById(rs.getInt(FILM_MPA_ID));

        Film film = new Film();
        film.setId(rs.getInt(FILM_ID));
        film.setName(rs.getString(FILM_NAME));
        film.setDescription(rs.getString(FILM_DESCRIPTION));
        film.setReleaseDate(Objects.requireNonNull(rs.getDate(FILM_RELEASE_DATE)).toLocalDate());
        film.setDuration(rs.getInt(FILM_DURATION));
        film.setRate(rs.getInt(FILM_RATE));
        film.setGenres(genres);
        film.setMpa(mpa);

        return film;
    }

    @Override
    public void delAllFilms() {
        jdbcTemplate.update("DELETE FROM films");
    }


}
