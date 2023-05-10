package ru.yandex.practicum.filmorate.exception.filmException;

public class InvalidFilmNameException extends Exception{
    public InvalidFilmNameException() { super(); }
    public InvalidFilmNameException(String message) { super(message); }
    public InvalidFilmNameException(String message, Throwable cause) { super(message, cause); }
    public InvalidFilmNameException(Throwable cause) { super(cause); }
}
