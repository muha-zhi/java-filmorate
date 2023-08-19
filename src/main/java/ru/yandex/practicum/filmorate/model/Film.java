package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
  
    @JsonIgnore
    @EqualsAndHashCode.Include
    private long id;
  
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
  
    @NotNull(message = "Описание фильма не может быть пустым")
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
  
    @NotNull(message = "Дата релиза не может быть пустым")
    private LocalDate releaseDate;
  
    @Positive(message = "продолжительность фильма должна быть положительной")
    private int duration;

    private List<Genre> genres;

    private Mpa mpa;

    private int rate;
  
}
