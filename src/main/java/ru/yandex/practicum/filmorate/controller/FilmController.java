package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.filmException.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j

@RestController
public class FilmController {

    Map<Integer, Film> films = new HashMap<>();

    int idOfAll = 0;

    public int getIdOfAll() {
        idOfAll += 1;
        return idOfAll;
    }


    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) throws Exception {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Пустое имя {}", film);
            throw new InvalidFilmNameException("Имя не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.warn("В описании фильма больше 200 символов {}", film);
            throw new DescriptionMore200Characters("максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза фильма раньше 12 декабря 1895 года {}", film);
            throw new FilmDateException("дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            log.warn("продолжительность фильма должна быть положительной {}", film);
            throw new DurationNegativeException("продолжительность фильма должна быть положительной");
        } else {
            if (film.getId() == 0) {
                film.setId(getIdOfAll());
            }
            log.info("Создан фильм с id {}      {}", film.getId(), film);
            films.put(film.getId(), film);
            return film;
        }
    }

    @GetMapping("/films")
    public List<Film> findAll() {

        return new ArrayList<>(films
                .values());
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws Exception {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Пустое имя {}", film);
            throw new InvalidFilmNameException("Имя не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.warn("В описании фильма больше 200 символов {}", film);
            throw new DescriptionMore200Characters("максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза фильма раньше 12 декабря 1895 года {}", film);
            throw new FilmDateException("дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            log.warn("продолжительность фильма должна быть положительной {}", film);
            throw new DurationNegativeException("продолжительность фильма должна быть положительной");
        } else if (!films.containsKey(film.getId())) {
            throw new AbsentFilmWithThisIdException("Фильм с id " + film.getId() + " не найден");
        } else {
            log.info("Обновлен фильм с id {}      {}", film.getId(), film);
            films.put(film.getId(), film);
            return film;
        }
    }

}
