package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.filmException.DescriptionMore200Characters;
import ru.yandex.practicum.filmorate.exception.filmException.DurationNegativeException;
import ru.yandex.practicum.filmorate.exception.filmException.FilmDateException;
import ru.yandex.practicum.filmorate.exception.filmException.InvalidFilmNameException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {
    FilmController controller;

    Film getFilm() {
        return Film.builder()
                .id(1)
                .name("Оно")
                .description("Страшно")
                .releaseDate(LocalDate.of(2000, 5, 12))
                .duration(90)
                .build();
    }

    @BeforeEach
    void afterEach() {
        controller = new FilmController();
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
        controller.updateFilm(Film.builder()
                .id(1)
                .name("Оно")
                .description("Не очень страшно")
                .releaseDate(LocalDate.of(2000, 5, 12))
                .duration(90)
                .build());

        assertEquals(1, controller.findAll().size());

        assertEquals(Film.builder()
                .id(1)
                .name("Оно")
                .description("Не очень страшно")
                .releaseDate(LocalDate.of(2000, 5, 12))
                .duration(90)
                .build(), controller.findAll().get(0));

    }

    @Test
    void shouldReturnDescriptionMore200CharacterEx() {
        Film film = Film.builder()
                .id(1)
                .name("Оно")
                .description("Страшнооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                        "оооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                        "оооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                        "оооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо")
                .releaseDate(LocalDate.of(2000, 5, 12))
                .duration(90)
                .build();

        final DescriptionMore200Characters exception = assertThrows(

                DescriptionMore200Characters.class,
                () -> controller.createFilm(film));
        assertEquals("максимальная длина описания — 200 символов", exception.getMessage());

    }

    @Test
    void shouldReturnDurationNegativeException() {
        Film film = Film.builder()
                .id(1)
                .name("Оно")
                .description("Страшно")
                .releaseDate(LocalDate.of(2000, 5, 12))
                .duration(-1)
                .build();

        final DurationNegativeException exception = assertThrows(

                DurationNegativeException.class,
                () -> controller.createFilm(film));
        assertEquals("продолжительность фильма должна быть положительной", exception.getMessage());

    }

    @Test
    void shouldReturnInvalidFilmNameException() {
        Film film = Film.builder()
                .id(1)
                .description("Страшно")
                .releaseDate(LocalDate.of(2000, 5, 12))
                .duration(90)
                .build();

        final InvalidFilmNameException exception = assertThrows(

                InvalidFilmNameException.class,
                () -> controller.createFilm(film));
        assertEquals("Имя не может быть пустым", exception.getMessage());

    }

    @Test
    void shouldReturnMovieDateException() {
        Film film = Film.builder()
                .id(1)
                .name("Оно")
                .description("Страшно")
                .releaseDate(LocalDate.of(1800, 5, 12))
                .duration(90)
                .build();

        final FilmDateException exception = assertThrows(

                FilmDateException.class,
                () -> controller.createFilm(film));
        assertEquals("дата релиза — не раньше 28 декабря 1895 года", exception.getMessage());

    }
}