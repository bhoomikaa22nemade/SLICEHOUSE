
package com.example.sliceeehouse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sliceeehouse.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);

}


