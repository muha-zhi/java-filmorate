package ru.yandex.practicum.filmorate.exception.filmException;

public class DescriptionMore200Characters extends Exception{
    public DescriptionMore200Characters() { super(); }
    public DescriptionMore200Characters(String message) { super(message); }
    public DescriptionMore200Characters(String message, Throwable cause) { super(message, cause); }
    public DescriptionMore200Characters(Throwable cause) { super(cause); }
}
