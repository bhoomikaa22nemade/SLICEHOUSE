package com.example.sliceeehouse.repository;

import com.example.sliceeehouse.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {

    Shipping findByTrackingNumber(String trackingNumber);

}
