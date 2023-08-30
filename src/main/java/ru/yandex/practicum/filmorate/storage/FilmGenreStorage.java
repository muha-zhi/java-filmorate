package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreStorage {

    void addFilmGenre(long idOfFilm, long idOfGenre);

    void delFilmGenre(long idOfFilm, long idOfGenre);

    List<Genre> getAllGenForFilm(long filmId);

    void updateGenresOfFilm(long filmId, List<Genre> genres);
}
