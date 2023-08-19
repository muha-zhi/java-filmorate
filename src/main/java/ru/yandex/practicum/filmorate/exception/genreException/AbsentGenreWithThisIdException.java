package ru.yandex.practicum.filmorate.exception.genreException;

public class AbsentGenreWithThisIdException extends RuntimeException {

    public AbsentGenreWithThisIdException() {
        super();
    }

    public AbsentGenreWithThisIdException(String message) {
        super(message);
    }

    public AbsentGenreWithThisIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsentGenreWithThisIdException(Throwable cause) {
        super(cause);
    }
}
