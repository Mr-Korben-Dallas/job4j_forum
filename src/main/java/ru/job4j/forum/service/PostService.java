package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostRepository;
import java.util.*;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Collection<Post> findAll() {
        return repository.findAll();
    }

    public void save(Post post) {
        repository.save(post);
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public void update(Post post) {
        repository.update(post);
    }
}
