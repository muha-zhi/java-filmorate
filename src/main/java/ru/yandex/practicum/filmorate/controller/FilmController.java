package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;


@RestController
public class FilmController {


    @Autowired
    FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }


    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) throws Exception {
        return service.createFilm(film);

    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return service.getAllFilms();
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws Exception {
        return service.updateFilm(film);
    }


    @PutMapping("/films/{id}/like/{userid}")
    public void addLikeFilm(@PathVariable long id,
                            @PathVariable long userid) {
        service.addLikeToFilm(id, userid);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film delLikeFilm(@PathVariable long id,
                            @PathVariable long userId) {
        return service.removeLikeToFilm(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        return service.getMostPopFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable long id) {
        return service.getFilmById(id);
    }


}
