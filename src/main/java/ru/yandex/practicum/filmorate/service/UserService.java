package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.userException.AbsentUserWithThisIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    @Qualifier("friendDbStorage")
    private final FriendStorage friendStorage;


    public void addToFriends(long idFriend, long idOfUser) {
        User user = userStorage.getUserById(idOfUser);
        User friend = userStorage.getUserById(idFriend);
        if (user == null) {
            log.warn("пользватель с id {} не найден", idOfUser);
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfUser + " не найден");
        } else if (friend == null) {
            log.warn("пользватель с id {} не найден", idFriend);
            throw new AbsentUserWithThisIdException("пользватель с id " + idFriend + " не найден");
        } else {
            friendStorage.addFriend(idFriend, idOfUser);
        }
    }

    public void removeFromFriends(long idOfFriend, long idOfUser) {
        User user = userStorage.getUserById(idOfUser);
        User friend = userStorage.getUserById(idOfFriend);
        if (user == null) {
            log.warn("пользватель с id {} не найден", idOfUser);
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfUser + " не найден");
        } else if (friend == null) {
            log.warn("пользватель с id {} не найден", idOfFriend);
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfFriend + " не найден");
        } else {
            friendStorage.removeFriendById(idOfFriend, idOfUser);
        }
    }

    public List<User> getCommonFriends(long idOfFirst, long idOfSecond) {
        User firstUser = userStorage.getUserById(idOfFirst);
        User secondUser = userStorage.getUserById(idOfSecond);

        if (firstUser == null) {
            log.warn("пользватель с id {} не найден", idOfFirst);
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfFirst + " не найден");
        } else if (secondUser == null) {
            log.warn("пользватель с id {} не найден", idOfSecond);
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfSecond + " не найден");
        } else {

            List<User> firstUserFriends = friendStorage.getAllFriendOfUser(idOfFirst);
            List<User> secondUserFriends = friendStorage.getAllFriendOfUser(idOfSecond);

            if (firstUserFriends != null && secondUserFriends != null) {
                return firstUserFriends.stream()
                        .filter(secondUserFriends::contains)
                        .collect(Collectors.toList());
            }
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
            return friendStorage.getAllFriendOfUser(id);
        }

    }

    public boolean delUserById(long id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + id + " не найден");
        } else {
            return userStorage.delUserById(id);
        }
    }
}
