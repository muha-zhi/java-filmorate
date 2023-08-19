package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.impl.dao.FriendDbStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserControllerTest {

    private UserController controller;

    private Validator validator;

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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        controller = new UserController(new UserService(new InMemoryUserStorage(), new FriendDbStorage(new JdbcTemplate())));
    }

    @Test
    void findAll() {
        User user = getUser();

        controller.createUser(user);
        assertEquals(user, controller.findAll().get(0));
    }


    @Test
    void createUser() {

        User user = getUser();

        controller.createUser(user);
        assertEquals(user, controller.findAll().get(0));
    }

    @Test
    void updateUser() {
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
        User user = getUser();
        user.setBirthday(LocalDate.of(2025, 7, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnInvalidEmailException() {
        User user = getUser();
        user.setEmail("logangmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnInvalidLogUserException() {
        User user = getUser();
        user.setLogin("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldAddLoginInsteadNameIfNameIsNull() {
        User user = getUser();
        user.setName("");

        controller.createUser(user);
        User user2 = controller.findAll().get(0);
        assertEquals("Logan", user2.getName());
    }


}