package ru.yandex.practicum.filmorate.controller;

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
public class UserController {

    int idOfAll = 0;

    public int getIdOfAll(){
        idOfAll += 1;
        return idOfAll;
    }

    Map<Integer, User> users = new Hashtable<>();

    @GetMapping("/users")
    public List<User> findAll() {
        List<User> listForReturn = new ArrayList<>();
        for(Integer k : users.keySet()){
            listForReturn.add(users.get(k));
        }
        return listForReturn;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new InvalidEmailException("электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new InvalidLogUserException("логин не может быть пустым и содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InvalidBirthdayException("дата рождения не может быть в будущем");
        } else {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(getIdOfAll());
            }
            users.put(user.getId(), user);
            return user;
        }
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new InvalidEmailException("электронная почта не может быть пустой и должна содержать символ @");

        } else if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new InvalidLogUserException("логин не может быть пустым и содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InvalidBirthdayException("дата рождения не может быть в будущем");
        } else if(!users.containsKey(user.getId())) {
            throw new AbsentUserWithThisIdException("ползователь с таким id отсутствует");
        }
        else {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            return user;
        }
    }


}
