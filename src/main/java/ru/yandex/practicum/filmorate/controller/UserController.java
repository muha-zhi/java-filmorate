package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.userException.AbsentUserWithThisIdException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidBirthdayException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidLogUserException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    int idOfAll = 0;

    public int getIdOfAll() {
        idOfAll += 1;
        return idOfAll;
    }

    Map<Integer, User> users = new Hashtable<>();

    @GetMapping("/users")
    public List<User> findAll() {

        return new ArrayList<>(users
                .values());
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("У пользователя отсутствует email");
            throw new InvalidEmailException("электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.warn("У пользователя отсутствует login");
            throw new InvalidLogUserException("логин не может быть пустым и содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("дата рождения не может быть в будущем");
            throw new InvalidBirthdayException("дата рождения не может быть в будущем");
        } else {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(getIdOfAll());
            }
            log.info("Пользватель с id {} создан {}", user.getId(), user);
            users.put(user.getId(), user);
            return user;
        }
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.info("У пользователя отсутствует email");
            throw new InvalidEmailException("электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.info("У пользователя отсутствует login");
            throw new InvalidLogUserException("логин не может быть пустым и содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("дата рождения не может быть в будущем");
            throw new InvalidBirthdayException("дата рождения не может быть в будущем");
        } else if (!users.containsKey(user.getId())) {
            throw new AbsentUserWithThisIdException("пользватель с id " + user.getId() + " не найден");
        } else {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            log.info("Пользватель с id {} обновлен {}", user.getId(), user);
            users.put(user.getId(), user);
            return user;
        }
    }


}
