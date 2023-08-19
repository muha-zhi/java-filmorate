package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;

@Component
public interface LikeStorage {

    void addLike(long userId, long filmId);

    void removeLike(long filmId, long userId);

    long getCountLikesOfFilm(long filmId);


}
