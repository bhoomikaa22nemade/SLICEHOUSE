package com.example.sliceeehouse.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(unique = true, nullable = false)
    private String code;        // e.g. CHEESE20

    @Column(nullable = false)
    private String type;        // PERCENT / FLAT / DELIVERY

    private double value;       // 20 (for 20%) or 50 (₹50)

    private double minAmount;   // minimum cart amount

    private boolean active;     // true = usable

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ================= AUTO TIMESTAMP =================
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ================= GETTERS =================
    public Long getCouponId() {
        return couponId;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ================= SETTERS =================
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
