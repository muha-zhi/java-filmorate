package ru.yandex.practicum.filmorate.exception.filmException;

public class FilmDateException extends Exception{
    public FilmDateException() { super(); }
    public FilmDateException(String message) { super(message); }
    public FilmDateException(String message, Throwable cause) { super(message, cause); }
    public FilmDateException(Throwable cause) { super(cause); }
}
