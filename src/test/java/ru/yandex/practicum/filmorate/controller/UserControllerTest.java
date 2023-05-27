package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.userException.InvalidBirthdayException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidLogUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {
    UserController controller;

    User getUser() {
        User user = new User();
        user.setId(1);
        user.setLogin("Logan");
        user.setEmail("logan@gmail.com");
        user.setName("Логан");
        user.setBirthday(LocalDate.of(2003, 7, 12));
        return user;
    }

    @BeforeEach
    void beforeEach() {
        controller = new UserController(new UserService(new InMemoryUserStorage()));
    }

    @Test
    void findAll() throws Exception {
        User user = getUser();

        controller.createUser(user);
        assertEquals(user, controller.findAll().get(0));
    }


    @Test
    void createUser() throws Exception {

        User user = getUser();

        controller.createUser(user);
        assertEquals(user, controller.findAll().get(0));
    }

    @Test
    void updateUser() throws Exception {
        User user = getUser();
        controller.createUser(user);
        assertEquals(1, controller.findAll().size());
        assertEquals(user, controller.findAll().get(0));

        User user2 = getUser();
        user2.setEmail("vanda@gmail.com");
        controller.updateUser(user2);
        assertEquals(1, controller.findAll().size());
        assertEquals(user2, controller.findAll().get(0));

    }

    @Test
    void shouldReturnInvalidBirthdayException() {
        User user =getUser();
        user.setBirthday(LocalDate.of(2025, 7, 12));
        final InvalidBirthdayException exception = assertThrows(

                InvalidBirthdayException.class,
                () -> controller.createUser(user));
        assertEquals("дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidEmailException() {
        User user = getUser();
        user.setEmail("logangmail.com");
        final InvalidEmailException exception = assertThrows(

                InvalidEmailException.class,
                () -> controller.createUser(user));
        assertEquals("электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidLogUserException() {
        User user = getUser();
        user.setLogin("");
        final InvalidLogUserException exception = assertThrows(

                InvalidLogUserException.class,
                () -> controller.createUser(user));
        assertEquals("логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    void shouldAddLoginInsteadNameIfNameIsNull() throws Exception {
        User user = getUser();
        user.setName("");

        controller.createUser(user);
        User user2 = controller.findAll().get(0);
        assertEquals("Logan", user2.getName());
    }


}