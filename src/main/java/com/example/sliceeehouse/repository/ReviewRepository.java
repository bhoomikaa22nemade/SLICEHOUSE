package com.example.sliceeehouse.repository;

import com.example.sliceeehouse.model.Review;
import com.example.sliceeehouse.model.Pizza;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPizzaAndStatus(Pizza pizza, Boolean status);

}
