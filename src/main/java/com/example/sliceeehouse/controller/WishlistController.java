package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.*;
import com.example.sliceeehouse.repository.*;
import com.example.sliceeehouse.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/wishlist")
public class WishlistController {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;
    private final CartRepository cartRepository;

    public WishlistController(WishlistRepository wishlistRepository,
                              UserRepository userRepository,
                              PizzaRepository pizzaRepository,
                              CartRepository cartRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.pizzaRepository = pizzaRepository;
        this.cartRepository = cartRepository;
    }

    // ================= ADD =================
    @PostMapping("/add")
    public String addToWishlist(@RequestParam Long pizzaId, Principal principal) {

        if (principal == null) return "redirect:/login";

        // ✅ FIXED TYPE
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));

        Wishlist existing = wishlistRepository.findByUserAndPizza(user, pizza);

        if (existing == null) {
            Wishlist w = new Wishlist();
            w.setUser(user);
            w.setPizza(pizza);
            wishlistRepository.save(w);
        }

        return "redirect:/admin/wishlist";
    }

    // ================= VIEW =================
    @GetMapping
    public String viewWishlist(Model model, Principal principal) {

        if (principal == null) return "redirect:/login";

        // ✅ FIXED TYPE
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Wishlist> items = wishlistRepository.findByUser(user);

        model.addAttribute("wishlistItems", items);

        return "admin/wishlist/view_wishlist";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        wishlistRepository.deleteById(id);
        return "redirect:/admin/wishlist";
    }

    // ================= MOVE TO CART =================
    @GetMapping("/move-to-cart/{id}")
    public String moveToCart(@PathVariable Long id) {

        Wishlist w = wishlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        Cart cart = new Cart();
        cart.setUser(w.getUser());
        cart.setPizza(w.getPizza());
        cart.setQuantity(1);
        cart.setSize("Regular");

        cartRepository.save(cart);

        wishlistRepository.delete(w);

        return "redirect:/admin/cart";
    }
}
