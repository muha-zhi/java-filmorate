package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InMemoryLikeStorage implements LikeStorage {
    private final List<Like> likes = new ArrayList<>();
    private long id = 0;

    private long getId() {
        return ++id;
    }


    @Override
    public void addLike(long userId, long filmId) {
        Like like = new Like();
        if (userId != 0 && filmId != 0) {
            like.setId(getId());
            like.setIdOfUser(userId);
            like.setIdOfFilm(filmId);
            likes.add(like);
        }
    }

    @Override
    public void removeLike(long filmId, long userId) {
        Like like = null;
        if (userId != 0 && filmId != 0) {
            for (Like l : likes) {
                if (l.getIdOfFilm() == filmId && l.getIdOfUser() == userId) {
                    like = l;
                    break;

                }
            }
        }
        if (like != null) {
            likes.remove(like);
        }
    }

    @Override
    public long getCountLikesOfFilm(long filmId) {
        long count = 0;
        for (Like like : likes) {
            if (like.getIdOfFilm() == filmId) {
                count++;
            }
        }
        return count;
    }
}
