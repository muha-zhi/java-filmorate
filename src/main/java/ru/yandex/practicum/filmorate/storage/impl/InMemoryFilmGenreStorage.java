package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmGenreStorage implements FilmGenreStorage {

    private final List<FilmGenre> filmGenres = new ArrayList<>();

    @Override
    public void addFG(long idOfFilm, long idOfGenre) {
        filmGenres.add(new FilmGenre(idOfFilm, idOfGenre));

    }

    @Override
    public void delFG(long idOfFilm, long idOfGenre) {
        filmGenres.removeIf(fg -> fg.getFilmId() == idOfFilm && fg.getGenreId() == idOfGenre);
    }

    @Override
    public List<Genre> getAllGenForFilm(long filmId) {
        return filmGenres.stream()
                .filter((FilmGenre fg) -> fg.getFilmId() == filmId)
                .map(fg -> new Genre(fg.getGenreId()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateGenresOfFilm(long filmId, List<Genre> genres) {
        filmGenres.removeIf(fg -> fg.getFilmId() == filmId);
        filmGenres.addAll(genres.stream()
                .map(g -> new FilmGenre(filmId, g.getId()))
                .collect(Collectors.toList()));
    }
}
