package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User addUser(User user);

    void delUserById(long id);

    User updateUser(User user);

    User getUserById(long id);


    List<User> getUsers();
}
