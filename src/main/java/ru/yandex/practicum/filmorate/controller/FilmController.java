package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Validated
public class FilmController {

    private final FilmService service;

    @PostMapping("/films")
    public Film createFilm(@RequestBody @Valid Film film) {
        return service.createFilm(film);

    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return service.getAllFilms();
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody @Valid Film film) {
        return service.updateFilm(film);
    }


    @PutMapping("/films/{id}/like/{userid}")
    public void addLikeFilm(@PathVariable long id,
                            @PathVariable long userid) {
        service.addLikeToFilm(id, userid);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void delLikeFilm(@PathVariable long id,
                            @PathVariable long userId) {
        service.removeLikeToFilm(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopFilms(@RequestParam(defaultValue = "10") @Positive int count) {
        return service.getMostPopFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable long id) {
        return service.getFilmById(id);
    }

    @DeleteMapping("/films/{id}")
    public void delFilmById(@PathVariable long id) {
        service.delFilmById(id);
    }

    @GetMapping("/mpa/{id}")
    public Mpa getRatingById(@PathVariable long id) {
        return service.getMpaById(id);
    }

    @PostMapping("/mpa")
    public void createRating(@RequestBody @Valid Mpa rating) {
        service.addMpa(rating);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllRating() {
        return service.getAllMpa();
    }

    @DeleteMapping("/mpa/{id}")
    public void delRatingById(@PathVariable long id) {
        service.delMpaById(id);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return service.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable long id) {
        return service.getGenreById(id);
    }

}