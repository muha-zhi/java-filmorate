package ru.yandex.practicum.filmorate.DbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.impl.dao.FilmDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmStorageDbTest {

    private final FilmDbStorage filmDbStorage;


    Film getFilm() {
        Film film = new Film();
        film.setName("Оно");
        film.setDescription("Страшно");
        film.setReleaseDate(LocalDate.of(2000, 5, 12));
        film.setDuration(90);
        film.setGenres(List.of(new Genre(1, "Комедия")));
        film.setRate(4);
        film.setMpa(new Mpa(2, "PG"));

        return film;

    }

    @AfterEach
    void afterEach() {
        filmDbStorage.delAllFilms();
    }


    @Test
    void createFilmTest() {
        Film film = getFilm();
        if (film != null) {
            filmDbStorage.addFilm(film);
        }
        assertEquals(film, filmDbStorage.getAllFilms().get(0));
    }

    @Test
    void updateFilm() {


        Film film = filmDbStorage.addFilm(getFilm());
        assertEquals(film, filmDbStorage.getAllFilms().get(0));
        assertEquals(1, filmDbStorage.getAllFilms().size());
        Film filmUpdate = film;
        filmUpdate.setDescription("Не очень страшно");
        filmDbStorage.updateFilm(filmUpdate);

        assertEquals(1, filmDbStorage.getAllFilms().size());
        Film filmUpdate2 = new Film();
        filmUpdate2.setId(film.getId());
        filmUpdate2.setName("Оно");
        filmUpdate2.setDescription("Не очень страшно");
        filmUpdate2.setReleaseDate(LocalDate.of(2000, 5, 12));
        filmUpdate2.setDuration(90);
        filmUpdate2.setGenres(List.of(new Genre(1, "Комедия")));
        filmUpdate2.setRate(4);
        filmUpdate2.setMpa(new Mpa(2, "PG"));

        assertEquals(filmUpdate2, filmDbStorage.getAllFilms().get(0));
    }

    @Test
    void delFilm() {


        Film film1 = filmDbStorage.addFilm(getFilm());
        assertEquals(film1, filmDbStorage.getAllFilms().get(0));

        filmDbStorage.delFilmById(film1.getId());

        assertNull(filmDbStorage.getFilmById(film1.getId()));
    }
}
