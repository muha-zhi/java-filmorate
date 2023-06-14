package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.filmException.FilmDateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {
    private FilmController controller;

    private Validator validator;

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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());

    }

    @Test
    void shouldReturnDurationNegativeException() {
        Film film = getFilm();
        film.setDuration(-1);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());

    }

    @Test
    void shouldReturnInvalidFilmNameException() {
        Film film = getFilm();
        film.setName("");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());

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