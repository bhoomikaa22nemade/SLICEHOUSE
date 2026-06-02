
// package com.example.sliceeehouse.controller;

// import java.security.Principal;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;

// import com.example.sliceeehouse.model.Address;
// import com.example.sliceeehouse.model.Order;
// import com.example.sliceeehouse.model.User;
// import com.example.sliceeehouse.repository.AddressRepository;
// import com.example.sliceeehouse.repository.OrderRepository;
// import com.example.sliceeehouse.repository.UserRepository;

// @Controller
// public class ProfileController {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private OrderRepository orderRepository;

//     @Autowired
//     private AddressRepository addressRepository;


 
 


//     // =========================
//     // VIEW ORDERS
//     // =========================
//     @GetMapping("/order")
//     public String orderPage(Model model, Principal principal) {

//         User user = userRepository.findByUser(principal.getName());

//         List<Order> orders = orderRepository.findByUser(user);

//         model.addAttribute("orders", orders);

//         return "order";
//     }


//     // =========================
//     // VIEW CART PAGE
//     // =========================
//     @GetMapping("/viewcart")
//     public String viewCart() {

//         return "viewcart";
//     }


//     // =========================
//     // VIEW ADDRESS PAGE
//     // =========================
//     @GetMapping("/address")
//     public String addressPage(Model model, Principal principal) {

//         User user = userRepository.findByEmail(principal.getName());

//         List<Address> addresses = addressRepository.findByUser(user);

//         model.addAttribute("addresses", addresses);

//         return "add_address";
//     }


//     // =========================
//     // SAVE ADDRESS
//     // =========================
//     @PostMapping("/saveAddress")
//     public String saveAddress(Address address, Principal principal) {

//         User user = userRepository.findByEmail(principal.getName());

//         address.setUser(user);

//         addressRepository.save(address);

//         return "redirect:/address";
//     }

// }

