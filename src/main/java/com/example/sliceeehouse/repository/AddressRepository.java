package com.example.sliceeehouse.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sliceeehouse.model.Address;
import com.example.sliceeehouse.model.User;


public interface AddressRepository extends JpaRepository<Address, Long> {
     List<Address> findAllByUser(User user);
}

