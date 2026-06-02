package com.example.sliceeehouse.repository;

import com.example.sliceeehouse.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // 🔍 Find coupon by code (used while applying coupon)
    Optional<Coupon> findByCode(String code);

    // 📋 Get all active coupons
    List<Coupon> findByActiveTrue();

    // ❌ Get all inactive coupons
    List<Coupon> findByActiveFalse();

    // 🔍 Check if coupon exists
    boolean existsByCode(String code);
}
