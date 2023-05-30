package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.filmException.AbsentFilmWithThisIdException;
import ru.yandex.practicum.filmorate.exception.filmException.FilmDateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;


    private final UserStorage userStorage;

    private final LocalDate filmsBirthday = LocalDate.of(1895, 12, 28);


    int idOfAll = 0;

    public int getIdOfAll() {
        return ++idOfAll;
    }

    public Film createFilm(Film film) throws Exception {
        if (film != null) {
            isTrueFimDate(film);
            if (film.getId() == 0) {
                film.setId(getIdOfAll());
            }

            if (filmStorage.getFilmById(film.getId()) != null) {
                log.info("Создан фильм с id {}   {}", film.getId(), film);
            }
            return filmStorage.addFilm(film);
        }
        return null;

    }


    public Film updateFilm(Film film) throws Exception {
        if (film != null) {
            isTrueFimDate(film);
            Film film1 = getFilmById(film.getId());
            log.info("Обновлен фильм с id {}", film1);
            return filmStorage.updateFilm(film);

        }
        return null;
    }

    public List<Film> getAllFilms() {
        log.info("Выполнен запрос на получение всех фильмов");
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(long id) {
        if (filmStorage.getFilmById(id) == null) {
            log.warn("Фильм с {} не найден", id);
            throw new AbsentFilmWithThisIdException("Фильм с id " + id + " не найден");
        }
        log.info("Фильм с {} найден", id);
        return filmStorage.getFilmById(id);
    }

    public void delFilmById(long id) {
        getFilmById(id);

        filmStorage.delFilmById(id);
        log.info("Фильм с {} удален", id);
    }

    public void addLikeToFilm(long idOfFilm, long idOfUser) {
        Film film = getFilmById(idOfFilm);
        User user = getUserById(idOfUser);
        Set<Long> likes = film.getLikesOfUsers();
        if (likes != null) {

            likes.add(user.getId());
            log.info("Пользователь с ID " + idOfUser + " поставил лайк фильму с ID " + idOfFilm);
        }

    }

    public Film removeLikeToFilm(long idOfFilm, long idOfUser) {
        Film film = getFilmById(idOfFilm);
        User user = getUserById(idOfUser);
        film.getLikesOfUsers().remove(user.getId());
        log.info("Пользователь с ID {} удалил лайк у фильма с ID {}", idOfUser, idOfFilm);
        return film;
    }

    public List<Film> getMostPopFilms(int from) {
        List<Film> allFilms = filmStorage.getAllFilms();

        if (allFilms != null) {
            int min = Math.min(from, allFilms.size());
            log.info("Выполнен на получение самых популярных фильмов {}", allFilms);
            return allFilms.stream()
                    .sorted(Comparator.comparing(Film::getLikes).reversed())
                    .limit(min)
                    .collect(Collectors.toList());


        }
        return null;
    }

    private void isTrueFimDate(Film film) {
        if (film.getReleaseDate().isBefore(filmsBirthday)) {
            log.warn("Дата релиза фильма раньше 12 декабря 1895 года {}", film);
            throw new FilmDateException("дата релиза — не раньше 28 декабря 1895 года");
        }

    }

    private User getUserById(long id) {
        if (userStorage.getUserById(id) == null) {
            log.warn("Пользователь с {} не найден", id);
            throw new AbsentFilmWithThisIdException("Пользователь с id " + id + " не найден");
        }
        log.info("Пользователь с {} найден", id);
        return userStorage.getUserById(id);
    }

}
