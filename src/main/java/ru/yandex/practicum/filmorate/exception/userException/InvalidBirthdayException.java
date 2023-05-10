package ru.yandex.practicum.filmorate.exception.userException;

public class InvalidBirthdayException extends Exception{
    public InvalidBirthdayException() { super(); }
    public InvalidBirthdayException(String message) { super(message); }
    public InvalidBirthdayException(String message, Throwable cause) { super(message, cause); }
    public InvalidBirthdayException(Throwable cause) { super(cause); }
}
