package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.User;
import com.example.sliceeehouse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CoreController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CoreController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }


 

    // REGISTER PAGE
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // SAVE USER
    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN");

        userRepository.save(user);

        return "redirect:/login";
    }
    
}


