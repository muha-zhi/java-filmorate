package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFriend {
    @JsonIgnore
    @EqualsAndHashCode.Include
    private long id;

    private long idOfUser;

    private long idOfFriend;
}
