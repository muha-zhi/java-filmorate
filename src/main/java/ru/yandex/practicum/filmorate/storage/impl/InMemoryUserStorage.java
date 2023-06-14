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
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private final Map<Long, User> users = new Hashtable<>();

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
        if(users.containsKey(id)){
            return false;
        }
        return true;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;

    }

}
