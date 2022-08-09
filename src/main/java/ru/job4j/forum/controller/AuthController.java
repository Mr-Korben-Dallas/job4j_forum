package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("user", new User());
        model.addAttribute("fail", fail != null);
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(Model model, @ModelAttribute User user, HttpServletRequest req) {
        Optional<User> regUser = userService.save(user);
        if (regUser.isEmpty()) {
            return "redirect:/signup?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", regUser.get());
        return "redirect:/index";
    }

    @GetMapping("/signin")
    public String signin(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("user", new User());
        model.addAttribute("fail", fail != null);
        return "auth/signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userOptional = userService.findByName(user.getName());
        if (userOptional.isEmpty()) {
            return "redirect:/signin?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/index";
    }

    @GetMapping("/signout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
