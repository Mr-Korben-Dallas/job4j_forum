package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.AuthService;
import ru.job4j.forum.service.PostService;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @GetMapping(value = {"/", "/index"})
    public String posts(Model model, HttpSession session) {
        Optional<User> user = authService.userFromSession(session);
        if (user.isEmpty()) {
            return "redirect:/signin";
        }
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("user", user.get());
        return "post/posts";
    }

    @GetMapping("/addpost")
    public String addPost(Model model, HttpSession session) {
        Optional<User> user = authService.userFromSession(session);
        if (user.isEmpty()) {
            return "redirect:/signin";
        }
        model.addAttribute("post", new Post());
        model.addAttribute("user", user.get());
        return "post/add";
    }

    @PostMapping("/addpost")
    public String addPost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/index";
    }

    @GetMapping("/updatepost/{postId}")
    public String updatePost(Model model, @PathVariable("postId") Long id, HttpSession session) {
        Optional<User> user = authService.userFromSession(session);
        if (user.isEmpty()) {
            return "redirect:/signin";
        }
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("user", user.get());
        return "post/update";
    }

    @PostMapping("/updatepost")
    public String updatePost(@ModelAttribute Post post) {
        postService.update(post);
        return "redirect:/index";
    }

    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable("id") Long id, HttpSession session) {
        Optional<User> user = authService.userFromSession(session);
        if (user.isEmpty()) {
            return "redirect:/signin";
        }
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            return "redirect:/index";
        }
        model.addAttribute("post", post.get());
        model.addAttribute("user", user.get());
        return "post/post";
    }
}
