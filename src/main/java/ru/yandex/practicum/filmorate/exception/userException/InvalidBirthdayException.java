package ru.yandex.practicum.filmorate.exception.userException;

import javax.validation.ValidationException;

public class InvalidBirthdayException extends ValidationException {
    public InvalidBirthdayException() {
        super();
    }

    public InvalidBirthdayException(String message) {
        super(message);
    }

    public InvalidBirthdayException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBirthdayException(Throwable cause) {
        super(cause);
    }
}
