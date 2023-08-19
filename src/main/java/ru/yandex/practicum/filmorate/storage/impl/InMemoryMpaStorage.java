package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InMemoryMpaStorage implements MpaStorage {

    private final List<Mpa> mpas = new ArrayList<>();

    @Override
    public void addMpa(Mpa mpa) {
        if (mpa != null) {
            mpas.add(mpa);
        }
    }

    @Override
    public Mpa getMpaById(long id) {
        Mpa m = null;
        if (id != 0) {
            for (Mpa mpa : mpas) {
                if (mpa.getId() == id) {
                    m = mpa;
                }
            }
        }
        return m;
    }

    @Override
    public List<Mpa> getAllMpa() {
        return mpas;
    }

    @Override
    public void delMpaById(long id) {
        mpas.removeIf(m -> m.getId() == id);
    }
}
