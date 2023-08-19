package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
public interface GenreStorage {
    void addGenre(Genre genre);

    Genre getGenreById(long id);

    List<Genre> getAllGenre();

    void delGenreById(long id);

}
