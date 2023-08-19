package ru.yandex.practicum.filmorate.exception.userException;

public class AbsentUserWithThisIdException extends RuntimeException {
    public AbsentUserWithThisIdException() {
        super();
    }

    public AbsentUserWithThisIdException(String message) {
        super(message);
    }

    public AbsentUserWithThisIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsentUserWithThisIdException(Throwable cause) {
        super(cause);
    }
}
