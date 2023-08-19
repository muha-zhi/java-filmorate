package ru.yandex.practicum.filmorate.exception.filmException;

import javax.validation.ValidationException;

public class FilmDateException extends ValidationException {
    public FilmDateException() {
        super();
    }

    public FilmDateException(String message) {
        super(message);
    }

    public FilmDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmDateException(Throwable cause) {
        super(cause);
    }
}
