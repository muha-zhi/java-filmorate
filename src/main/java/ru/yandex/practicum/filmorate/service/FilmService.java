package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.filmException.AbsentFilmWithThisIdException;
import ru.yandex.practicum.filmorate.exception.filmException.FilmDateException;
import ru.yandex.practicum.filmorate.exception.genreException.AbsentGenreWithThisIdException;
import ru.yandex.practicum.filmorate.exception.mpaException.AbsentMpaWithThisIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    @Qualifier("mpaDbStorage")
    private final MpaStorage mpaStorage;

    @Qualifier("likeDbStorage")
    private final LikeStorage likeStorage;

    @Qualifier("genreDbStorage")
    private final GenreStorage genreStorage;

    private final LocalDate filmsBirthday = LocalDate.of(1895, 12, 28);

    public Film createFilm(Film film) {
        if (film != null) {
            isTrueFimDate(film);
            if (filmStorage.getFilmById(film.getId()) == null) {
                log.info("Создан фильм с id {}   {}", film.getId(), film);
                return filmStorage.addFilm(film);
            }
            log.warn("Уже сущестует фильм с id {}   {}", film.getId(), film);
        }
        return null;

    }


    public Film updateFilm(Film film) {
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
        final Film film = filmStorage.getFilmById(id);
        if (film == null) {
            log.warn("Фильм с {} не найден", id);
            throw new AbsentFilmWithThisIdException("Фильм с id " + id + " не найден");
        }
        log.info("Фильм с {} найден", id);
        return film;
    }

    public void delFilmById(long id) {
        getFilmById(id);
        filmStorage.delFilmById(id);
        log.info("Фильм с {} удален", id);
    }

    public void addLikeToFilm(long idOfFilm, long idOfUser) {
        getUserById(idOfUser);
        getFilmById(idOfFilm);
        likeStorage.addLike(idOfUser, idOfFilm);
        log.info("Пользватель с ID {} поставил лайк фильму c ID {}", idOfUser, idOfFilm);

    }

    public void removeLikeToFilm(long idOfFilm, long idOfUser) {
        getUserById(idOfUser);
        getFilmById(idOfFilm);
        likeStorage.removeLike(idOfFilm, idOfUser);
        log.info("Пользователь с ID {} удалил лайк у фильма с ID {}", idOfUser, idOfFilm);
    }

    public List<Film> getMostPopFilms(int from) {
        List<Film> allFilms = filmStorage.getAllFilms();
        if (allFilms != null) {
            int min = Math.min(from, allFilms.size());
            log.info("Выполнен запрос на получение самых популярных фильмов {}", allFilms);
            return allFilms.stream()
                    .sorted(Comparator.comparing((Film f) -> likeStorage.getCountLikesOfFilm(f.getId())).reversed())
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
        final User user = userStorage.getUserById(id);
        if (user == null) {
            log.warn("Пользователь с {} не найден", id);
            throw new AbsentFilmWithThisIdException("Пользователь с id " + id + " не найден");
        }
        log.info("Пользователь с {} найден", id);
        return user;
    }

    public Mpa getMpaById(long id) {
        Mpa mpa = mpaStorage.getMpaById(id);
        if (mpa == null) {
            log.warn("MPA с id {} не найден", id);
            throw new AbsentMpaWithThisIdException("MPA с id " + id + " не найден");
        }
        log.info("MPA с id {} найден", id);
        return mpaStorage.getMpaById(id);
    }

    public void addMpa(Mpa mpa) {
        mpaStorage.addMpa(mpa);
        log.info("Был добвлен MPA ID - {}, NAME - {}", mpa.getId(), mpa.getName());
    }

    public void delMpaById(long id) {
        getMpaById(id);
        mpaStorage.delMpaById(id);
        log.info("MPA c ID {} удален", id);
    }

    public List<Mpa> getAllMpa() {
        log.info("Выполнен запрос на получение всех рейтингов");
        return mpaStorage.getAllMpa();
    }

    public Genre getGenreById(long id) {
        Genre genre = genreStorage.getGenreById(id);
        if (genre != null) {
            log.info("Жанр '{}' с id {} - найден", genre.getName(), genre.getId());
            return genre;
        } else {
            throw new AbsentGenreWithThisIdException("Жанр с id " + id + " - не найден");
        }

    }

    public List<Genre> getAllGenres() {
        log.info("Выполнен запрос на получение всех рейтингов");
        return genreStorage.getAllGenre();
    }
}
