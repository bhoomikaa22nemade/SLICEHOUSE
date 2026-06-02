package com.example.sliceeehouse.model;

import com.example.sliceeehouse.model.enums.OrderStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "order_id")   // 🔥 ADD THIS
private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    private Integer quantity;

    private LocalDateTime orderAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Order() {
        this.orderAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public void setUser(User user2) {
    this.user = user2;
}
}
