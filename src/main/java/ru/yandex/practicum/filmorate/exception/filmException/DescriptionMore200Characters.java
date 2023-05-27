package ru.yandex.practicum.filmorate.exception.filmException;

import javax.validation.ValidationException;

public class DescriptionMore200Characters extends ValidationException {
    public DescriptionMore200Characters() {
        super();
    }

    public DescriptionMore200Characters(String message) {
        super(message);
    }

    public DescriptionMore200Characters(String message, Throwable cause) {
        super(message, cause);
    }

    public DescriptionMore200Characters(Throwable cause) {
        super(cause);
    }
}
