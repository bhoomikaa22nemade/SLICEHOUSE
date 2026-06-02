package com.example.sliceeehouse.repository;

import com.example.sliceeehouse.model.Wishlist;
import com.example.sliceeehouse.model.User;
import com.example.sliceeehouse.model.Pizza;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);   // ✅ IMPORTANT

    Wishlist findByUserAndPizza(User user, Pizza pizza);
}
