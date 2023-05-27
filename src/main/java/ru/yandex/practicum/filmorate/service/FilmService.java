package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.filmException.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    @Autowired
    FilmStorage filmStorage;

    @Autowired
    UserStorage userStorage;


    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    int idOfAll = 0;

    public int getIdOfAll() {
        return ++idOfAll;
    }

    public Film createFilm(Film film) throws Exception {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Пустое имя {}", film);
            throw new InvalidFilmNameException("Имя не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.warn("В описании фильма больше 200 символов {}", film);
            throw new DescriptionMore200Characters("максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза фильма раньше 12 декабря 1895 года {}", film);
            throw new FilmDateException("дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            log.warn("продолжительность фильма должна быть положительной {}", film);
            throw new DurationNegativeException("продолжительность фильма должна быть положительной");
        } else {
            if (film.getId() == 0) {
                film.setId(getIdOfAll());
            }

            if (filmStorage.getFilmById(film.getId()) != null) {
                log.info("Создан фильм с id {}   {}", film.getId(), film);
            }
            return filmStorage.addFilm(film);
        }
    }

    public Film updateFilm(Film film) throws Exception {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Пустое имя {}", film);
            throw new InvalidFilmNameException("Имя не может быть пустым");
        } else if (film.getDescription().length() > 200) {
            log.warn("В описании фильма больше 200 символов {}", film);
            throw new DescriptionMore200Characters("максимальная длина описания — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза фильма раньше 12 декабря 1895 года {}", film);
            throw new FilmDateException("дата релиза — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            log.warn("продолжительность фильма должна быть положительной {}", film);
            throw new DurationNegativeException("продолжительность фильма должна быть положительной");
        } else if (filmStorage.getFilmById(film.getId()) == null) {
            throw new AbsentFilmWithThisIdException("Фильм с id " + film.getId() + " не найден");
        } else {
            log.info("Обновлен фильм с id {}      {}", film.getId(), film);
            return filmStorage.updateFilm(film);
        }
    }

    public List<Film> getAllFilms() {
        log.info("Выполнен запрос на получение всех фильмов");
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(long id) {
        if (filmStorage.getFilmById(id) == null) {
            log.warn("Фильм с id " + id + " не найден");
            throw new AbsentFilmWithThisIdException("Фильм с id " + id + " не найден");
        }
        log.info("Фильм с id " + id + " найден");
        return filmStorage.getFilmById(id);
    }

    public void delFilmById(long id) {
        if (filmStorage.getFilmById(id) == null) {
            log.warn("Удаление фильма : Фильм с id " + id + " не найден");
            throw new AbsentFilmWithThisIdException("Удаление фильма : Фильм с id " + id + " не найден");
        }

        filmStorage.delFilmById(id);
        log.info("Удаление фильма : Фильм с id " + id + " удален");
    }

    public void addLikeToFilm(long idOfFilm, long idOfUser) {
        Film film = filmStorage.getFilmById(idOfFilm);
        if (film == null) {
            log.warn("Добавление лайка : Фильм с id " + idOfFilm + " не найден");
            throw new AbsentFilmWithThisIdException("Фильм с id " + idOfFilm + " не найден");
        } else if (userStorage.getUserById(idOfUser) == null) {
            log.warn("Удаление лайка фильма : Пользователь с id " + idOfUser + " не найден");
            throw new AbsentFilmWithThisIdException("Пользователь с id " + idOfUser + " не найден");
        }

        Set<Long> likes = film.getLikesOfUsers();
        if (likes != null) {

            likes.add(idOfUser);
            log.info("Пользователь с ID " + idOfUser + " поставил лайк фильму с ID " + idOfFilm);
        }

    }

    public Film removeLikeToFilm(long idOfFilm, long idOfUser) {
        Film film = filmStorage.getFilmById(idOfFilm);
        if (film == null) {
            log.warn("Удаление лайка фильма : Фильм с id " + idOfFilm + " не найден");
            throw new AbsentFilmWithThisIdException("Фильм с id " + idOfFilm + " не найден");
        } else if (userStorage.getUserById(idOfUser) == null) {
            log.warn("Удаление лайка фильма : Пользователь с id " + idOfUser + " не найден");
            throw new AbsentFilmWithThisIdException("Пользователь с id " + idOfUser + " не найден");
        }
        film.getLikesOfUsers().remove(idOfUser);
        log.info("Пользователь с ID " + idOfUser + " удалил лайк у фильма с ID " + idOfFilm);
        return film;
    }

    public List<Film> getMostPopFilms(int from) {
        List<Film> allFilms = filmStorage.getAllFilms();

        if (allFilms != null) {
            allFilms.sort(Comparator.comparing(Film::getLikes));
            Collections.reverse(allFilms);
            int min = Math.min(from, allFilms.size());
            log.info("Выполнен на получение самых популярных фильмов" + allFilms);
            return allFilms.stream()
                    .limit(min)
                    .collect(Collectors.toList());


        }
        return null;
    }


}