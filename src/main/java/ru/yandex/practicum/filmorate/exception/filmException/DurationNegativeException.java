package ru.yandex.practicum.filmorate.exception.filmException;

public class DurationNegativeException extends Exception{
    public DurationNegativeException() { super(); }
    public DurationNegativeException(String message) { super(message); }
    public DurationNegativeException(String message, Throwable cause) { super(message, cause); }
    public DurationNegativeException(Throwable cause) { super(cause); }
}
