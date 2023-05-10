package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@Builder
public class User {


    int id;
    @Email
    String email;
    @EqualsAndHashCode.Exclude

    String login;

    @EqualsAndHashCode.Exclude
    String name;

    @EqualsAndHashCode.Exclude
    LocalDate birthday;

}
