package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.filmException.DescriptionMore200Characters;
import ru.yandex.practicum.filmorate.exception.filmException.DurationNegativeException;
import ru.yandex.practicum.filmorate.exception.filmException.FilmDateException;
import ru.yandex.practicum.filmorate.exception.filmException.InvalidFilmNameException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {
    FilmController controller;

    Film getFilm() {
        Film film = new Film();
        film.setId(1);
        film.setName("Оно");
        film.setDescription("Страшно");
        film.setReleaseDate(LocalDate.of(2000, 5, 12));
        film.setDuration(90);

        return film;

    }

    @BeforeEach
    void afterEach() {
        controller = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
    }

    @Test
    void createFilm() throws Exception {

        Film film = getFilm();

        controller.createFilm(film);
        assertEquals(film, controller.findAll().get(0));


    }

    @Test
    void findAll() throws Exception {
        Film film = getFilm();

        controller.createFilm(film);
        assertEquals(film, controller.findAll().get(0));

    }

    @Test
    void updateFilm() throws Exception {
        Film film = getFilm();

        controller.createFilm(film);
        assertEquals(film, controller.findAll().get(0));
        assertEquals(1, controller.findAll().size());
        Film filmUpdate = getFilm();
        filmUpdate.setDescription("Не очень страшно");
        controller.updateFilm(filmUpdate);

        assertEquals(1, controller.findAll().size());
        Film filmUpdate2 = new Film();
        filmUpdate2.setId(1);
        filmUpdate2.setName("Оно");
        filmUpdate2.setDescription("Не очень страшно");
        filmUpdate2.setReleaseDate(LocalDate.of(2000, 5, 12));
        filmUpdate2.setDuration(90);
        assertEquals(filmUpdate2, controller.findAll().get(0));

    }

    @Test
    void shouldReturnDescriptionMore200CharacterEx() {
        Film film = getFilm();
        film.setDescription("Страшнооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                "оооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                "оооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                "оооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо");

        final DescriptionMore200Characters exception = assertThrows(

                DescriptionMore200Characters.class,
                () -> controller.createFilm(film));
        assertEquals("максимальная длина описания — 200 символов", exception.getMessage());

    }

    @Test
    void shouldReturnDurationNegativeException() {
        Film film = getFilm();
        film.setDuration(-1);

        final DurationNegativeException exception = assertThrows(

                DurationNegativeException.class,
                () -> controller.createFilm(film));
        assertEquals("продолжительность фильма должна быть положительной", exception.getMessage());

    }

    @Test
    void shouldReturnInvalidFilmNameException() {
        Film film = getFilm();
        film.setName("");

        final InvalidFilmNameException exception = assertThrows(

                InvalidFilmNameException.class,
                () -> controller.createFilm(film));
        assertEquals("Имя не может быть пустым", exception.getMessage());

    }

    @Test
    void shouldReturnMovieDateException() {
        Film film = getFilm();
        film.setReleaseDate(LocalDate.of(1800, 5, 12));

        final FilmDateException exception = assertThrows(

                FilmDateException.class,
                () -> controller.createFilm(film));
        assertEquals("дата релиза — не раньше 28 декабря 1895 года", exception.getMessage());

    }
}