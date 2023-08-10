package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public interface FilmStorage {

    Film getFilmById(long id);

    Film addFilm(Film film);

    void delFilmById(long id);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    void delAllFilms();


}
