package ru.yandex.practicum.filmorate.exception.filmException;

import javax.validation.ValidationException;

public class DurationNegativeException extends ValidationException {
    public DurationNegativeException() { super(); }
    public DurationNegativeException(String message) { super(message); }
    public DurationNegativeException(String message, Throwable cause) { super(message, cause); }
    public DurationNegativeException(Throwable cause) { super(cause); }
}
