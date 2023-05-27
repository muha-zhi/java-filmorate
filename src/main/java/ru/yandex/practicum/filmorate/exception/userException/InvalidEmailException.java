package ru.yandex.practicum.filmorate.exception.userException;

import javax.validation.ValidationException;

public class InvalidEmailException extends ValidationException {

    public InvalidEmailException() { super(); }
    public InvalidEmailException(String message) { super(message); }
    public InvalidEmailException(String message, Throwable cause) { super(message, cause); }
    public InvalidEmailException(Throwable cause) { super(cause); }
}
