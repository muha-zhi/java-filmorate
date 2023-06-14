package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.userException.AbsentUserWithThisIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;




    public void addToFriends(long idOfFirst, long idOfSecond) {
        User firstUser = getUserById(idOfFirst);
        User secondUser = getUserById(idOfSecond);

        firstUser.addFriend(idOfSecond);
        secondUser.addFriend(idOfFirst);


    }

    public User removeFromFriends(long idOfFirst, long idOfSecond) {
        User firstUser = getUserById(idOfFirst);
        User secondUser = getUserById(idOfSecond);

        firstUser.getFriends().remove(secondUser.getId());
        secondUser.getFriends().remove(firstUser.getId());

        return firstUser;
    }

    public List<User> getCommonFriends(long idOfFirst, long idOfSecond) {
        User firstUser = getUserById(idOfFirst);
        User secondUser = getUserById(idOfSecond);

        Set<Long> firstUserFriends = firstUser.getFriends();
        Set<Long> secondUserFriends = secondUser.getFriends();


        if (firstUserFriends != null && secondUserFriends != null) {
            return firstUserFriends.stream()
                    .filter(secondUserFriends::contains)
                    .map(userStorage::getUserById)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        if (user != null) {

            if (user.getName() == null || user.getName().isBlank()) {

                user.setName(user.getLogin());
            }
            log.info("Пользватель с id {} создан {}", user.getId(), user);
            return userStorage.addUser(user);

        }
        return null;
    }

    public User updateUser(User user) {
        if (user != null) {

            if (getUserById(user.getId()) != null) {
                if (user.getName() == null) {
                    user.setName(user.getLogin());
                }
                log.info("Пользватель с id {} обновлен {}", user.getId(), user);
                return userStorage.updateUser(user);

            }
        }
        return null;
    }

    public User getUserById(long id) {
        if (id != 0) {
            if (userStorage.getUserById(id) == null) {
                log.warn("пользватель с id {} не найден", id);
                throw new AbsentUserWithThisIdException("пользватель с id " + id + " не найден");
            } else {
                return userStorage.getUserById(id);
            }
        }
        return null;
    }

    public List<User> getMyFriends(long id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + id + " не найден");
        } else {
            return user.getFriends().stream()
                    .map(this::getUserById)
                    .collect(Collectors.toList());
        }

    }

    public boolean delUserById(long id){
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + id + " не найден");
        } else {
            return userStorage.delUserById(id);
        }
    }


}
