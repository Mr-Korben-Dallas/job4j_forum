package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final AtomicLong postId = new AtomicLong();
    private final Map<Long, Post> posts = new HashMap<>();

    public PostRepository() {
        posts.put(postId.incrementAndGet(), new Post(postId.get(), "Продаю машину ладу 01.", "Era moris, tanquam salvus pes."));
        posts.put(postId.incrementAndGet(), new Post(postId.get(), "Продаю машину ладу 02.", "Frondators experimentum, tanquam teres vortex."));
        posts.put(postId.incrementAndGet(), new Post(postId.get(), "Продаю машину ладу 03.", "Sunt fraticinidaes pugna audax, ferox racanaes."));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void save(Post post) {
        post.setId(postId.incrementAndGet());
        posts.put(postId.get(), post);
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}
