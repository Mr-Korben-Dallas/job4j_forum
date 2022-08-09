package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.AuthRepository;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository repository;

    public AuthService(AuthRepository repository) {
        this.repository = repository;
    }

    public Optional<User> userFromSession(HttpSession session) {
        return repository.userFromSession(session);
    }
}
