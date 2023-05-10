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

    public int getIdOfAll(){
        idOfAll += 1;
        return idOfAll;
    }


    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) throws Exception {
        if (film.getName() == null || film.getName().isBlank()) {

            throw new InvalidFilmNameException("Имя не может быть пустым");
        } else if (film.getDescription().length() > 200) {

            throw new DescriptionMore200Characters("максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmDateException("дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            throw new DurationNegativeException("продолжительность фильма должна быть положительной");
        } else {
            if(film.getId() == 0){
                film.setId(getIdOfAll());
            }
            films.put(film.getId(), film);
            return film;
        }
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        List<Film> listForReturn = new ArrayList<>();
        for (Integer k : films.keySet()){
            listForReturn.add(films.get(k));
        }
        return listForReturn;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws Exception{
        if (film.getName().isBlank() || film.getName() == null) {
            throw new InvalidFilmNameException("Имя не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new DescriptionMore200Characters("максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmDateException("дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            throw new DurationNegativeException("продолжительность фильма должна быть положительной");
        } else if (!films.containsKey(film.getId())) {
            throw new AbsentFilmWithThisIdException("отсутствует фильм с таким id");
        } else {
            films.put(film.getId(), film);
            return film;
        }
    }

}
