package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.User;

import com.example.sliceeehouse.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= VIEW USERS =================

    @GetMapping
    public String viewUsers(Model model) {

        model.addAttribute("users", userRepository.findAll());

        return "admin/user/view_user";
    }


    // ================= ADD USER FORM =================

    @GetMapping("/add")
    public String addUserForm(Model model) {

        model.addAttribute("user", new User());

        return "admin/user/add_user";
    }


    // ================= SAVE USER =================

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {

        userRepository.save(user);

        return "redirect:/admin/users";
    }


    // ================= UPDATE USER FORM =================

    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);

        return "admin/user/update_user";
    }

    // ================= DELETE USER =================

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);

        return "redirect:/admin/users";
    }
}
