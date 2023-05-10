package ru.yandex.practicum.filmorate.exception.userException;

public class InvalidLogUserException extends Exception{
    public InvalidLogUserException() { super(); }
    public InvalidLogUserException(String message) { super(message); }
    public InvalidLogUserException(String message, Throwable cause) { super(message, cause); }
    public InvalidLogUserException(Throwable cause) { super(cause); }
}
