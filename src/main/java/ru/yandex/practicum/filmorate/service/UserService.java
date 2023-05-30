package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.userException.AbsentUserWithThisIdException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidBirthdayException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {


    private final UserStorage userStorage;
    private int idOfAll = 0;

    private int getIdOfAll() {

        return ++idOfAll;
    }

    public void addToFriends(long idOfFirst, long idOfSecond) throws ValidationException {
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

    public User createUser(User user) throws Exception {
        if (user != null) {

            userValid(user);

            if (user.getName() == null || user.getName().isBlank()) {

                user.setName(user.getLogin());
            }
            if (user.getId() == 0) {
                user.setId(getIdOfAll());
            }
            log.info("Пользватель с id {} создан {}", user.getId(), user);
            return userStorage.addUser(user);

        }
        return null;
    }

    public User updateUser(User user) throws Exception {
        if (user != null) {
            userValid(user);
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

    public User getUserById(long id) throws ValidationException {
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

    private void userValid(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("дата рождения не может быть в будущем");
            throw new InvalidBirthdayException("дата рождения не может быть в будущем");
        }
    }


}
