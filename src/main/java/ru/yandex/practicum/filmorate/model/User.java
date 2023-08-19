package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonIgnore
    @EqualsAndHashCode.Include
    private long id;

    @NotEmpty(message = "Пустое поле Email")
    @Email(message = "Почта не соответсвует формату email")
    private String email;
  
    @NotBlank(message = "логин не может быть пустым и содержать пробелы")
    @Pattern(regexp = "^\\w+$", message = "логин не может быть пустым и содержать пробелы")
    private String login;

    private String name;

    @PastOrPresent(message = "дата рождения не может быть в будущем")
    @NotNull(message = "дата рождения не может быть пустым")
    private LocalDate birthday;

}
