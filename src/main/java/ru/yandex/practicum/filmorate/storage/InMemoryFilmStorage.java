package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();


    @Override
    public Film getFilmById(long id) {
        return films.get(id);
    }

    @Override
    public Film addFilm(Film film) {

        films.put(film.getId(), film);
        return film;

    }

    @Override
    public void delFilmById(long id) {
        films.remove(id);

    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;

    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }


}
