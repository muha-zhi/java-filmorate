package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class MpaController {

    private final FilmService service;

    @GetMapping("/mpa/{id}")
    public Mpa getRatingById(@PathVariable long id) {
        return service.getMpaById(id);
    }

    @PostMapping("/mpa")
    public void createRating(@RequestBody @Valid Mpa rating) {
        service.addMpa(rating);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllRating() {
        return service.getAllMpa();
    }

    @DeleteMapping("/mpa/{id}")
    public void delRatingById(@PathVariable long id) {
        service.delMpaById(id);
    }
}
