package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Cart;
import com.example.sliceeehouse.model.Order;
import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.User;
import com.example.sliceeehouse.model.enums.OrderStatus;
import com.example.sliceeehouse.repository.CartRepository;
import com.example.sliceeehouse.repository.OrderRepository;
import com.example.sliceeehouse.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserOrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserOrderController(OrderRepository orderRepository,
                               UserRepository userRepository,
                               CartRepository cartRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    // USER ORDER PAGE
    @GetMapping("/order")
    public String viewOrders(Model model, Principal principal) {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        model.addAttribute("orders", orders);

        return "order";
    }

    // PLACE ORDER AFTER PAYMENT
@GetMapping("/order/place")
public String placeOrder(Principal principal) {

    User user = userRepository
            .findByUsername(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    List<Cart> cartItems = cartRepository.findByUser(user);

    for (Cart cart : cartItems) {

        Order order = new Order();

        order.setUser(user);
        order.setPizza(cart.getPizza());
        order.setQuantity(cart.getQuantity());
        order.setOrderAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);
    }

    cartRepository.deleteAll(cartItems);

    return "redirect:/order";
}



}