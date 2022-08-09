package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final AtomicLong userId = new AtomicLong();
    private final Map<Long, User> users = new HashMap<>();

    public Optional<User> save(User user) {
        users.put(userId.incrementAndGet(), user);
        return Optional.ofNullable(user);
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public Optional<User> findByName(User user) {
        Optional<Map.Entry<Long, User>> entryUser = users
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getName().equals(user.getName()))
                .findFirst();
        return entryUser.map(Map.Entry::getValue);
    }
}
