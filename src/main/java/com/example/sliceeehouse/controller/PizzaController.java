package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.enums.Category;
import com.example.sliceeehouse.repository.PizzaRepository;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/pizzas")
public class PizzaController {

    private final PizzaRepository pizzaRepository;

    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping
    public String viewPizzas(Model model) {
        model.addAttribute("pizzas", pizzaRepository.findAll());
        return "admin/pizza/view_pizza";
    }

    @GetMapping("/add")
    public String addPizzaForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("categories", Category.values());
        return "admin/pizza/add_pizza";
    }

    @PostMapping("/add")
    public String addPizza(@ModelAttribute Pizza pizza,
                           @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            pizza.setProductImage(imageFile.getBytes());
        }

        pizzaRepository.save(pizza);
        return "redirect:/admin/pizzas";
    }

    @GetMapping("/update/{id}")
    public String updatePizzaForm(@PathVariable Long id, Model model) {
        Pizza pizza = pizzaRepository.findById(id).orElse(new Pizza());
        model.addAttribute("pizza", pizza);
        model.addAttribute("categories", Category.values());
        return "admin/pizza/update_pizza";
    }

    @PostMapping("/update")
    public String updatePizza(@ModelAttribute Pizza pizza,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            pizza.setProductImage(imageFile.getBytes());
        }

        pizzaRepository.save(pizza);
        return "redirect:/admin/pizzas";
    }

    @GetMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaRepository.deleteById(id);
        return "redirect:/admin/pizzas";
    }

    @GetMapping("/images/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow();
        return pizza.getProductImage();
    }
}