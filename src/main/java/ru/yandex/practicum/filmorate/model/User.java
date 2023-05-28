package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@Builder
public class User {


   private int id;
    @Email
   private String email;
    @EqualsAndHashCode.Exclude
   private String login;

    @EqualsAndHashCode.Exclude
   private String name;

    @EqualsAndHashCode.Exclude
   private LocalDate birthday;

}
