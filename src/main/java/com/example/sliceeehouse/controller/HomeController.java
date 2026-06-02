package com.example.sliceeehouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.enums.Category;
import com.example.sliceeehouse.repository.PizzaRepository;

// ✅ ADD THIS IMPORT
import com.example.sliceeehouse.repository.CategoryRepository;

import org.springframework.http.MediaType;

@Controller
public class HomeController {

    private final PizzaRepository pizzaRepository;

    // ✅ ADD THIS
    private final CategoryRepository categoryRepository;

    // ✅ UPDATE CONSTRUCTOR
    public HomeController(PizzaRepository pizzaRepository,
                          CategoryRepository categoryRepository) {
        this.pizzaRepository = pizzaRepository;
        this.categoryRepository = categoryRepository;
    }

    // ================= HOME PAGE =================
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("cheesePizzas",
                pizzaRepository.findByCategory(Category.CHEESE_PIZZA));

        model.addAttribute("pepperoniPizzas",
                pizzaRepository.findByCategory(Category.PEPPERONI_PIZZA));

        model.addAttribute("veggiePizzas",
                pizzaRepository.findByCategory(Category.VEGGIE));

        model.addAttribute("maniaPizzas",
                pizzaRepository.findByCategory(Category.MANIA));

        // ✅ ADD THIS LINE ONLY (NEW FEATURE 🔥)
        model.addAttribute("categories",
                categoryRepository.findByStatusTrue());

        return "index";
    }

    // ================= CHEESE PIZZA PAGE =================
    @GetMapping("/cheese")
    public String cheese(Model model) {

        model.addAttribute("cheesePizzas",
                pizzaRepository.findByCategory(Category.CHEESE_PIZZA));

        return "cheese";
    }

    @GetMapping("/pepperoni")
    public String pepperoni(Model model) {

        model.addAttribute("pepperoniPizzas",
                pizzaRepository.findByCategory(Category.PEPPERONI_PIZZA));

        return "pepperoni";
    }

    @GetMapping("/vegie")
    public String veggie(Model model) {

        model.addAttribute("veggiePizzas",
                pizzaRepository.findByCategory(Category.VEGGIE));

        return "vegie";
    }

    @GetMapping("/mania")
    public String mania(Model model) {

        model.addAttribute("maniaPizzas",
                pizzaRepository.findByCategory(Category.MANIA));

        return "mania";
    }

    // ================= PIZZA DETAILS PAGE =================
    @GetMapping("/pizza/{id}")
    public String pizzaDetails(@PathVariable Long id, Model model) {

        Pizza pizza = pizzaRepository.findById(id).orElseThrow();

        model.addAttribute("pizza", pizza);

        return "pizza_details";
    }

    @GetMapping("/pizza/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getPizzaImage(@PathVariable Long id) {

        Pizza pizza = pizzaRepository.findById(id).orElse(null);

        if (pizza == null || pizza.getProductImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(pizza.getProductImage());
    }
}
