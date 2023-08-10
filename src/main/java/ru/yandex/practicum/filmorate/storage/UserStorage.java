package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public interface UserStorage {

    User addUser(User user);

    boolean delUserById(long id);

    User updateUser(User user);

    User getUserById(long id);


    List<User> getUsers();

    void delAllUsers();


}
