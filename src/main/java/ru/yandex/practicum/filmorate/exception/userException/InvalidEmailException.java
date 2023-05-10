package ru.yandex.practicum.filmorate.exception.userException;
public class InvalidEmailException extends Exception{

    public InvalidEmailException() { super(); }
    public InvalidEmailException(String message) { super(message); }
    public InvalidEmailException(String message, Throwable cause) { super(message, cause); }
    public InvalidEmailException(Throwable cause) { super(cause); }
}
