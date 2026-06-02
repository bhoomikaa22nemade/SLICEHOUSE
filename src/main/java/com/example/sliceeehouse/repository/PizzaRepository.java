package com.example.sliceeehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.enums.Category;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findByCategory(Category category);

}