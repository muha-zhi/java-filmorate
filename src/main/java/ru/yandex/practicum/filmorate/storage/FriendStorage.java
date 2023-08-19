package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public interface FriendStorage {

    List<User> getAllFriendOfUser(long userId);

    void removeFriendById(long friendId, long userId);

    void addFriend(long friendId, long userId);
}
