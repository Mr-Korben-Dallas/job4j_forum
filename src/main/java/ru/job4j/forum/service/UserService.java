package ru.job4j.forum.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        } catch (DataIntegrityViolationException e) {
            ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
            String failedField = cause.getConstraintName();
            throw new DataIntegrityViolationException(failedField);
        }
        return Optional.empty();
    }
}
