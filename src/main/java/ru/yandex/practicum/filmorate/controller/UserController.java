package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return service.getAllUsers();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) throws Exception {
        return service.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws Exception {
        return service.updateUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id,
                          @PathVariable long friendId) {
        service.addToFriends(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User delFriend(@PathVariable long id,
                          @PathVariable long friendId) {
        return service.removeFromFriends(id, friendId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable long id,
                                       @PathVariable long otherId) {
        return service.getCommonFriends(id, otherId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getMyFriends(@PathVariable long id) {
        return service.getMyFriends(id);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        return service.getUserById(id);
    }
}
