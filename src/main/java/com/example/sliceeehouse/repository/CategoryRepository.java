package com.example.sliceeehouse.repository;

import com.example.sliceeehouse.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByStatusTrue();
}