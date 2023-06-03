package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.filmException.AbsentFilmWithThisIdException;
import ru.yandex.practicum.filmorate.exception.userException.AbsentUserWithThisIdException;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@RestControllerAdvice("ru.yandex.practicum.filmorate.controller")
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({AbsentFilmWithThisIdException.class, AbsentUserWithThisIdException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(final RuntimeException e) {
        log.debug("Получен статус 404 NOT FOUND {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.debug("Получен статус 400 BAD REQUEST {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowable(final Throwable e) {
        log.debug("Получен статус 500 INTERNAL SERVER ERROR {}", e.getMessage(), e);
        return Map.of("error", e.getMessage());
    }
}
