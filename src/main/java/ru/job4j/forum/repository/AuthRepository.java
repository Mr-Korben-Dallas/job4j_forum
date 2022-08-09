package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.User;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Repository
public class AuthRepository {
    public Optional<User> userFromSession(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute("user"));
    }
}
