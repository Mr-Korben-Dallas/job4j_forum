package ru.job4j.forum.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> save(User user) {
        user.setEnabled(true);
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(repository.save(user));
    }

    public Optional<User> findByUsername(String name) {
        return Optional.ofNullable(repository.findByUsername(name));
    }
}
