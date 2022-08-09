package ru.job4j.forum.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;
import java.util.Optional;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("posts", postService.findAll());
        return "post/posts";
    }

    @GetMapping("/addpost")
    public String addPost(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "post/add";
    }

    @PostMapping("/addpost")
    public String addPost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/index";
    }

    @GetMapping("/updatepost/{postId}")
    public String updatePost(Model model, @PathVariable("postId") Long id) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("post", postService.findById(id));
        return "post/update";
    }

    @PostMapping("/updatepost")
    public String updatePost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/index";
    }

    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable("id") Long id) {
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            return "redirect:/index";
        }
        model.addAttribute("post", post.get());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "post/post";
    }
}
