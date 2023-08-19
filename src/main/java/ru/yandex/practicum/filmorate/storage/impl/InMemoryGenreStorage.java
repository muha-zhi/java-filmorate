package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InMemoryGenreStorage implements GenreStorage {

    private final List<Genre> genres = new ArrayList<>();

    private long id = 0;

    private long getId() {
        return ++id;
    }

    @Override
    public void addGenre(Genre genre) {
        if (genre != null) {
            genres.add(new Genre(getId()));
        }
    }

    @Override
    public Genre getGenreById(long id) {
        for (Genre genre : genres) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        return null;
    }

    @Override
    public List<Genre> getAllGenre() {
        return genres;
    }

    @Override
    public void delGenreById(long id) {
        for (Genre genre : genres) {
            if (genre.getId() == id) {
                genres.remove(genre);
            }
        }
    }
}
