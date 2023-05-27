package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film getFilmById(long id);

    Film addFilm(Film film) throws Exception;

    void delFilmById(long id);

    Film updateFilm(Film film) throws Exception;

    List<Film> getAllFilms();
}
