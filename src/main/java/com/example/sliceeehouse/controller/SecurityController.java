// package com.example.sliceeehouse.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.example.sliceeehouse.model.User;
// import com.example.sliceeehouse.repository.UserRepository;

// @Controller
// public class SecurityController {

// private final UserRepository userRepository;
// private final PasswordEncoder passwordEncoder;

// public SecurityController(UserRepository userRepository,
//                           PasswordEncoder passwordEncoder) {
//     this.userRepository = userRepository;
//     this.passwordEncoder = passwordEncoder;
// }

// // ================= LOGIN PAGE =================
// @GetMapping("/login")
// public String login() {
//     return "login";
// }

// // ================= REGISTER PAGE =================
// @GetMapping("/register")
// public String registerForm() {
//     return "register";
// }

// // ================= REGISTER USER =================
// @PostMapping("/register")
// public String registerUser(@RequestParam String username,
//                            @RequestParam String password) {

//     User user = new User();
//     user.setUsername(username);
//     user.setPassword(passwordEncoder.encode(password));
//     user.setRole("USER");   // default role

//     userRepository.save(user);

//     return "redirect:/login";
// }

// // ================= ADMIN DASHBOARD =================
// @GetMapping("/admin")
// public String adminDashboard() {
//     return "admin/admin";
// }


// }

