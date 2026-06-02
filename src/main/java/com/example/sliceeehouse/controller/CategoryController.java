package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Category;
import com.example.sliceeehouse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository repo;

    // Dashboard Page
    @GetMapping
    public String viewPage(Model model) {
        model.addAttribute("categories", repo.findByStatusTrue());
        model.addAttribute("category", new Category());
        return "category";
    }

    // Save Category
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setStatus(true);

        // ✅ IMAGE WILL ALSO SAVE (important)
        repo.save(category);

        return "redirect:/category";
    }

    // Edit Category
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable int id, Model model) {
        Category cat = repo.findById(id).orElseThrow();
        model.addAttribute("category", cat);
        model.addAttribute("categories", repo.findByStatusTrue());
        return "category";
    }

    // Update Category
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category) {
        Category cat = repo.findById(category.getCategoryId()).orElseThrow();

        cat.setCategoryName(category.getCategoryName());
        cat.setDescription(category.getDescription());

        // ✅ VERY IMPORTANT: update image also
        cat.setImage(category.getImage());

        cat.setUpdatedAt(LocalDateTime.now());

        repo.save(cat);

        return "redirect:/category";
    }

    // Soft Delete
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        Category cat = repo.findById(id).orElseThrow();
        cat.setStatus(false);
        repo.save(cat);
        return "redirect:/category";
    }
}
