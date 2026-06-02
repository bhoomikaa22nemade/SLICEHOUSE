package com.example.sliceeehouse.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Pizza pizza;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ✅ GETTERS

    public Long getWishlistId() {
        return wishlistId;
    }

    public User getUser() {
        return user;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ✅ SETTERS

    public void setUser(User user) {
        this.user = user;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
