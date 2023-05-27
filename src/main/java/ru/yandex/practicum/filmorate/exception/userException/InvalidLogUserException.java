package ru.yandex.practicum.filmorate.exception.userException;

import javax.validation.ValidationException;

public class InvalidLogUserException extends ValidationException {
    public InvalidLogUserException() { super(); }
    public InvalidLogUserException(String message) { super(message); }
    public InvalidLogUserException(String message, Throwable cause) { super(message, cause); }
    public InvalidLogUserException(Throwable cause) { super(cause); }
}
