package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.userException.AbsentUserWithThisIdException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidBirthdayException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidLogUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserStorage userStorage;
    int idOfAll = 0;

    public int getIdOfAll() {

        return ++idOfAll;
    }

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addToFriends(long idOfFirst, long idOfSecond) throws ValidationException {
        User firstUser = userStorage.getUserById(idOfFirst);
        User secondUser = userStorage.getUserById(idOfSecond);
        if (firstUser == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfFirst + " не найден");
        } else if (secondUser == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfSecond + " не найден");
        } else {
            firstUser.addFriend(idOfSecond);
            secondUser.addFriend(idOfFirst);
        }


    }

    public User removeFromFriends(long idOfFirst, long idOfSecond) {
        User firstUser = userStorage.getUserById(idOfFirst);
        User secondUser = userStorage.getUserById(idOfSecond);
        if (firstUser == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfFirst + " не найден");
        } else if (secondUser == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfSecond + " не найден");
        } else {
            firstUser.getFriends().remove(secondUser.getId());
            secondUser.getFriends().remove(firstUser.getId());
        }
        return firstUser;
    }

    public List<User> getCommonFriends(long idOfFirst, long idOfSecond) {

        if (userStorage.getUserById(idOfFirst) == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfFirst + " не найден");
        } else if (userStorage.getUserById(idOfSecond) == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + idOfSecond + " не найден");
        } else {
            Set<Long> firstUser = userStorage.getUserById(idOfFirst).getFriends();
            Set<Long> secondUser = userStorage.getUserById(idOfSecond).getFriends();

            List<User> usersForRet = new ArrayList<>();
            if (firstUser != null && secondUser != null) {
                List<Long> commonFriendsId = firstUser.stream()
                        .filter(secondUser::contains)
                        .collect(Collectors.toList());
                for (Long id : commonFriendsId) {
                    usersForRet.add(userStorage.getUserById(id));
                }
            }
            return usersForRet;
        }
    }

    public List<User> getAllUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.info("У пользователя отсутствует email");
            throw new InvalidEmailException("электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.info("У пользователя отсутствует login");
            throw new InvalidLogUserException("логин не может быть пустым и содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("дата рождения не может быть в будущем");
            throw new InvalidBirthdayException("дата рождения не может быть в будущем");
        } else {
            if (user.getName() == null || user.getName().isBlank()) {

                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(getIdOfAll());
            }
            log.info("Пользватель с id {} создан {}", user.getId(), user);
            return userStorage.addUser(user);
        }
    }

    public User updateUser(User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.info("У пользователя отсутствует email");
            throw new InvalidEmailException("электронная почта не может быть пустой и должна содержать символ @");
        } else if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.info("У пользователя отсутствует login");
            throw new InvalidLogUserException("логин не может быть пустым и содержать пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("дата рождения не может быть в будущем");
            throw new InvalidBirthdayException("дата рождения не может быть в будущем");
        } else if (userStorage.getUserById(user.getId()) == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + user.getId() + " не найден");
        } else {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            log.info("Пользватель с id {} обновлен {}", user.getId(), user);
            return userStorage.updateUser(user);

        }
    }

    public User getUserById(long id) throws ValidationException {
        if (userStorage.getUserById(id) == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + id + " не найден");
        } else {
            return userStorage.getUserById(id);
        }
    }

    public List<User> getMyFriends(long id) throws ValidationException {
        List<User> myUsers = new ArrayList<>();
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new AbsentUserWithThisIdException("пользватель с id " + id + " не найден");
        } else {
            Set<Long> userFriends = user.getFriends();
            if (userFriends != null) {
                for (Long fId : userFriends) {
                    User user2 = userStorage.getUserById(fId);
                    if (user2 != null) {
                        myUsers.add(user2);
                    }
                }
            }

        }
        return myUsers;
    }
}
