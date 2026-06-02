package com.example.sliceeehouse.repository;

import com.example.sliceeehouse.model.Order;
import com.example.sliceeehouse.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

}


