package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class Film {

    @EqualsAndHashCode.Include
    private long id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private int duration;

    private Set<Long> likesOfUsers = new HashSet<>();

    public int getLikes() {
        if (likesOfUsers != null) {
            return likesOfUsers.size();
        }
        return 0;
    }
}
