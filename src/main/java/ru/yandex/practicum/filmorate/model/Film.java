package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Builder
@Data
public class Film {

    @EqualsAndHashCode.Include
    int id;

    String name;

    String description;

    LocalDate releaseDate;

    int duration;
}
