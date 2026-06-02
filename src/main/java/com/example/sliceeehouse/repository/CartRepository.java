// package com.example.sliceeehouse.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.example.sliceeehouse.model.Cart;
// import com.example.sliceeehouse.model.Pizza;
// import com.example.sliceeehouse.model.User;

// public interface CartRepository extends JpaRepository<Cart, Long>{

//     List<Cart> findByUser(User user);

// Optional<Cart> findByUserAndPizzaAndSize(User user, Pizza pizza, String size);

// }

// package com.example.sliceeehouse.repository;

// import com.example.sliceeehouse.model.Cart;
// import com.example.sliceeehouse.model.User;

// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

// public interface CartRepository extends JpaRepository<Cart, Long> {

//     // 🔥 Get cart items of a specific user
//     List<Cart> findByUser(User user);

// }

package com.example.sliceeehouse.repository;

import com.example.sliceeehouse.model.Cart;
import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndPizzaAndSize(
            User user,
            Pizza pizza,
            String size
    );
}