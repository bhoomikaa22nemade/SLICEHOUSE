package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Review;
import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.User;
import com.example.sliceeehouse.repository.ReviewRepository;
import com.example.sliceeehouse.repository.PizzaRepository;
import com.example.sliceeehouse.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;

    public ReviewController(ReviewRepository reviewRepository,
                            PizzaRepository pizzaRepository,
                            UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.pizzaRepository = pizzaRepository;
        this.userRepository = userRepository;
    }

    // ================= ADD REVIEW =================
   @PostMapping("/add")
public String addReview(@RequestParam Long pizzaId,
                       @RequestParam int rating,
                       @RequestParam String reviewText,
                       Principal principal) {
    try {
        System.out.println(">>> HIT /review/add");

        if (principal == null) return "redirect:/login";

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));

        Review review = new Review();
        review.setUser(user);
        review.setPizza(pizza);
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setStatus(false);

        reviewRepository.save(review);

        System.out.println(">>> SAVED OK, redirecting...");
        return "redirect:/pizza/details/" + pizzaId;

    } catch (Exception e) {
        e.printStackTrace(); // 👈 THIS WILL SHOW REAL ERROR
        return "redirect:/pizza/details/" + pizzaId;
    }
}
    // ================= ADMIN VIEW =================
    @GetMapping("/admin")
    public String viewAllReviews(Model model) {

        model.addAttribute("reviews", reviewRepository.findAll());

        return "admin/review/view_reviews";
    }

    // ================= APPROVE REVIEW =================
    @GetMapping("/approve/{id}")
    public String approveReview(@PathVariable Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setStatus(true);
        reviewRepository.save(review);

        return "redirect:/review/admin";
    }

    // ================= DELETE REVIEW =================
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {

        reviewRepository.deleteById(id);

        return "redirect:/review/admin";
    }
}
