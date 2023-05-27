package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    @EqualsAndHashCode.Include
    private long id;
    @Email
    private String email;

    private String login;

    private String name;

    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();

    public void addFriend(long idOfFriend) {
        if (friends != null) {
            friends.add(idOfFriend);
        }

    }

}
