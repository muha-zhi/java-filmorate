package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new Hashtable<>();
    private long id = 0;

    private long getId() {
        return ++id;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void delAllUsers() {
        users.clear();
    }

    @Override
    public User getUserById(long id) {
        return users.get(id);
    }

    @Override
    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delUserById(long id) {
        users.remove(id);
        return !users.containsKey(id);
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;

    }

}
