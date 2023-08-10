package ru.yandex.practicum.filmorate.exception.mpaException;

public class AbsentMpaWithThisIdException extends RuntimeException {
    public AbsentMpaWithThisIdException() {
        super();
    }

    public AbsentMpaWithThisIdException(String message) {
        super(message);
    }

    public AbsentMpaWithThisIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsentMpaWithThisIdException(Throwable cause) {
        super(cause);
    }
}
