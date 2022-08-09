package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> save(User user) {
        return Optional.of(repository.save(user));
    }

    public Optional<User> findByName(String name) {
        return Optional.ofNullable(repository.findByName(name));
    }
}
