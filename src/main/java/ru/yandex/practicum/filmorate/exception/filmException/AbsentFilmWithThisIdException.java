package ru.yandex.practicum.filmorate.exception.filmException;

public class AbsentFilmWithThisIdException extends RuntimeException {
    public AbsentFilmWithThisIdException() {
        super();
    }

    public AbsentFilmWithThisIdException(String message) {
        super(message);
    }

    public AbsentFilmWithThisIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsentFilmWithThisIdException(Throwable cause) {
        super(cause);
    }
}
