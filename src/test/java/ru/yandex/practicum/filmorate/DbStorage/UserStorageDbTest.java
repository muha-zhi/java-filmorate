package ru.yandex.practicum.filmorate.DbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.dao.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserStorageDbTest {
    private final UserDbStorage userDbStorage;


    User getUser() {
        User user = new User();
        user.setLogin("Logan");
        user.setEmail("logan@gmail.com");
        user.setName("Логан");
        user.setBirthday(LocalDate.of(2003, 7, 12));
        return user;
    }

    @AfterEach
    void afterEach() {
        userDbStorage.delAllUsers();
    }

    @Test
    void createUser() {
        User user = userDbStorage.addUser(getUser());
        assertEquals(user, userDbStorage.getUserById(user.getId()));
    }

    @Test
    void updateUser() {
        User user = userDbStorage.addUser(getUser());
        assertEquals(1, userDbStorage.getUsers().size());
        assertEquals(user, userDbStorage.getUserById(user.getId()));

        User user2 = user;
        user2.setEmail("vanda@gmail.com");
        userDbStorage.updateUser(user2);
        assertEquals(1, userDbStorage.getUsers().size());
        assertEquals(user2, userDbStorage.getUserById(user2.getId()));
    }

    @Test
    void delUser() {
        User user = userDbStorage.addUser(getUser());
        assertEquals(1, userDbStorage.getUsers().size());
        assertEquals(user, userDbStorage.getUserById(user.getId()));
        userDbStorage.delUserById(user.getId());
        assertNull(userDbStorage.getUserById(user.getId()));
    }


}
