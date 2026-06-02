
// package com.example.sliceeehouse.model;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "cart")
// public class Cart {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;


//     // ================= USER =================
//     @ManyToOne
//     @JoinColumn(name = "user_id")
//     private User user;


//     // ================= PIZZA =================
//     @ManyToOne
//     @JoinColumn(name = "pizza_id")
//     private Pizza pizza;


//     // ================= SIZE =================
//     @Column(name = "size")
//     private String size;


//     // ================= QUANTITY =================
//     @Column(name = "quantity")
//     private int quantity;


//     // ================= COUPON =================
//     @Column(name = "coupon")
//     private String coupon;


//     // ================= CONSTRUCTORS =================
//     public Cart() {
//     }


//     // ================= GETTERS & SETTERS =================

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }


//     public User getUser() {
//         return user;
//     }

//     public void setUser(User user) {
//         this.user = user;
//     }


//     public Pizza getPizza() {
//         return pizza;
//     }

//     public void setPizza(Pizza pizza) {
//         this.pizza = pizza;
//     }


//     public String getSize() {
//         return size;
//     }

//     public void setSize(String size) {
//         this.size = size;
//     }


//     public int getQuantity() {
//         return quantity;
//     }

//     public void setQuantity(int quantity) {
//         this.quantity = quantity;
//     }


//     public String getCoupon() {
//         return coupon;
//     }

//     public void setCoupon(String coupon) {
//         this.coupon = coupon;
//     }
    
// }



package com.example.sliceeehouse.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // USER
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // PIZZA
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    // SIZE
    private String size;

    // QUANTITY
    private int quantity;

    // TOTAL PRICE
    private Double totalPrice;

    // COUPON
    private String coupon;

    // TIMESTAMP
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

    // GETTERS & SETTERS
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Pizza getPizza() { return pizza; }
    public void setPizza(Pizza pizza) { this.pizza = pizza; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getCoupon() { return coupon; }
    public void setCoupon(String coupon) { this.coupon = coupon; }
}