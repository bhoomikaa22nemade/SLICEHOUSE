
package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Order;
import com.example.sliceeehouse.model.Pizza;
import com.example.sliceeehouse.model.enums.OrderStatus;
import com.example.sliceeehouse.model.User; 
import com.example.sliceeehouse.model.Address;
import com.example.sliceeehouse.repository.OrderRepository;
import com.example.sliceeehouse.repository.PizzaRepository;
import com.example.sliceeehouse.repository.UserRepository;
import com.example.sliceeehouse.repository.AddressRepository;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepository orderRepository,
                           PizzaRepository pizzaRepository,
                           AddressRepository addressRepository,
                           UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // ================= VIEW ORDERS =================

    @GetMapping
    public String viewOrders(Model model) {

        model.addAttribute("orders", orderRepository.findAll());

        return "admin/order/view_order";
    }


    // ================= ADD ORDER FORM =================

    @GetMapping("/add")
    public String addOrderForm(Model model) {

        model.addAttribute("order", new Order());
        model.addAttribute("pizzas", pizzaRepository.findAll());
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("users", userRepository.findAll());

        return "admin/order/add_order";
    }


    // ================= SAVE ORDER =================

@PostMapping("/add")
public String addOrder(@RequestParam Long userId,
                       @RequestParam Long pizzaId,
                       @RequestParam Long addressId,
                       @RequestParam Integer quantity) {

    Order order = new Order();

    order.setUser(userRepository.findById(userId).orElse(null));
    order.setPizza(pizzaRepository.findById(pizzaId).orElse(null));
    order.setAddress(addressRepository.findById(addressId).orElse(null));
    order.setQuantity(quantity);

    orderRepository.save(order);

    return "redirect:/admin/orders";
}


    // ================= UPDATE ORDER FORM =================

    @GetMapping("/update/{id}")
    public String updateOrderForm(@PathVariable Long id, Model model) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        model.addAttribute("order", order);
        model.addAttribute("pizzas", pizzaRepository.findAll());
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("users", userRepository.findAll());

        return "admin/order/update_order";
    }


    // ================= UPDATE ORDER =================

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute Order order) {

        orderRepository.save(order);

        return "redirect:/admin/orders";
    }


    // ================= DELETE ORDER =================

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {

        orderRepository.deleteById(id);

        return "redirect:/admin/orders";
    }
    
@PostMapping("/place-order")
public String placeOrder(@RequestParam Long pizzaId,
                         @RequestParam int quantity,
                         @RequestParam Long addressId,
                         Principal principal) {

  User user = userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Pizza pizza = pizzaRepository.findById(pizzaId).orElse(null);
    Address address = addressRepository.findById(addressId).orElse(null);

    Order order = new Order();
    order.setUser(user);
    order.setPizza(pizza);
    order.setAddress(address);
    order.setQuantity(quantity);

    orderRepository.save(order);

    return "redirect:/my-orders";
}

@PostMapping("/update-status")
public String updateStatus(@RequestParam Long orderId,
                           @RequestParam OrderStatus status) {

    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    order.setStatus(status);
    orderRepository.save(order);

    return "redirect:/admin/orders";
}


}
