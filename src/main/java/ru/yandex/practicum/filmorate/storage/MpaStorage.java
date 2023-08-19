package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
@Component
public interface MpaStorage {

    void addMpa(Mpa rating);

    Mpa getMpaById(long id);

    List<Mpa> getAllMpa();

    void delMpaById(long id);
}
