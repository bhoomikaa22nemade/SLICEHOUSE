package com.example.sliceeehouse.controller;

import com.example.sliceeehouse.model.Shipping;
import com.example.sliceeehouse.model.Order;
import com.example.sliceeehouse.repository.ShippingRepository;
import com.example.sliceeehouse.repository.OrderRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/shipping")
public class ShippingController {

    private final ShippingRepository shippingRepository;
    private final OrderRepository orderRepository;

    public ShippingController(ShippingRepository shippingRepository,
                              OrderRepository orderRepository) {
        this.shippingRepository = shippingRepository;
        this.orderRepository = orderRepository;
    }

    // VIEW
    @GetMapping
    public String viewShipping(Model model){
        model.addAttribute("shippingList", shippingRepository.findAll());
        return "admin/shipping/view_shipping";
    }

    // ADD FORM
    @GetMapping("/add/{orderId}")
    public String addForm(@PathVariable Long orderId, Model model){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Shipping shipping = new Shipping();
        shipping.setOrder(order); // ✅ bind order here

        model.addAttribute("shipping", shipping); // ✅ IMPORTANT

        return "admin/shipping/add_shipping";
    }

    // SAVE
@PostMapping("/save")
public String save(@ModelAttribute Shipping shipping,
                   @RequestParam("order.id") Long orderId) {

    // ✅ fetch correct order from DB
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    // ✅ set order properly
    shipping.setOrder(order);

    // ✅ save
    shippingRepository.save(shipping);

    return "redirect:/admin/shipping";
}

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        shippingRepository.deleteById(id);
        return "redirect:/admin/shipping";
    }
}
