package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.userException.InvalidBirthdayException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.userException.InvalidLogUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {
    UserController controller;

    User getUser() {
        return User.builder()
                .id(1)
                .login("Logan123")
                .name("Логан")
                .email("logan@gmail.com")
                .birthday(LocalDate.of(2003, 7, 12))
                .build();
    }

    @BeforeEach
    void beforeEach() {
        controller = new UserController();
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

        User user2 = User.builder()
                .id(1)
                .login("Logan123")
                .name("Ванда")
                .email("vanda@gmail.com")
                .birthday(LocalDate.of(2003, 7, 12))
                .build();
        controller.updateUser(user2);
        assertEquals(1, controller.findAll().size());
        assertEquals(user2, controller.findAll().get(0));

    }

    @Test
    void shouldReturnInvalidBirthdayException() {
        User user = User.builder()
                .id(1)
                .login("Logan")
                .name("Логан")
                .email("logan@gmail.com")
                .birthday(LocalDate.of(2025, 7, 12))
                .build();
        final InvalidBirthdayException exception = assertThrows(

                InvalidBirthdayException.class,
                () -> controller.createUser(user));
        assertEquals("дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidEmailException() {
        User user = User.builder()
                .id(1)
                .login("Logan")
                .name("Логан")
                .email("logangmail.com")
                .birthday(LocalDate.of(2003, 7, 12))
                .build();
        final InvalidEmailException exception = assertThrows(

                InvalidEmailException.class,
                () -> controller.createUser(user));
        assertEquals("электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidLogUserException() {
        User user = User.builder()
                .id(1)
                .login("")
                .name("Логан")
                .email("logan@gmail.com")
                .birthday(LocalDate.of(2003, 7, 12))
                .build();
        final InvalidLogUserException exception = assertThrows(

                InvalidLogUserException.class,
                () -> controller.createUser(user));
        assertEquals("логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    void shouldAddLoginInsteadNameIfNameIsNull() throws Exception {
        User user = User.builder()
                .id(1)
                .login("Logan")
                .email("logan@gmail.com")
                .birthday(LocalDate.of(2003, 7, 12))
                .build();

        controller.createUser(user);
        User user2 = controller.findAll().get(0);
        assertEquals("Logan", user2.getName());
    }


}